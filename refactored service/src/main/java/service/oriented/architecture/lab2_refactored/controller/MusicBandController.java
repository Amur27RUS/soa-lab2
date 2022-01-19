package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.oriented.architecture.lab2_refactored.dto.MusicBandDTO;
import service.oriented.architecture.lab2_refactored.dto.PagedMusicBandList;
import service.oriented.architecture.lab2_refactored.dto.dtoList.MusicBandDTOList;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;
import service.oriented.architecture.lab2_refactored.exceptions.XmlParseException;
import service.oriented.architecture.lab2_refactored.mapper.MusicBandMapper;
import service.oriented.architecture.lab2_refactored.service.MusicBandService;
import service.oriented.architecture.lab2_refactored.xml.XmlToJavaConverter;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/musicBands", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class MusicBandController {

    private final MusicBandService musicBandService;
    private final MusicBandMapper musicBandMapper;
    private XmlToJavaConverter xmlToJava;

    public MusicBandController(MusicBandService musicBandService,
                           MusicBandMapper musicBandMapper) {
        this.musicBandService = musicBandService;
        this.musicBandMapper = musicBandMapper;
        this.xmlToJava = new XmlToJavaConverter();
    }

    @GetMapping
    public Object getMusicBands(@RequestParam(name = "perPage", required = false) Integer perPage,
                                @RequestParam(name = "curPage", required = false) Integer curPage,
                                @RequestParam(name = "sortBy", required = false) String sortBy,
                                @RequestParam(name = "filterBy", required = false) String filterBy) throws IOException {
        System.out.println("FILTER BY" + filterBy);

        if (sortBy != null && !sortBy.equals("name") && !sortBy.equals("id") && !sortBy.equals("singles") && !sortBy.equals("numberOfParticipants") && !sortBy.equals("genre")
                && !sortBy.equals("date") && !sortBy.equals("coordinate") && !sortBy.equals("bestAlbum")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (filterBy != null && !filterBy.split(",")[0].equals("name") && !filterBy.split(",")[0].equals("id") && !filterBy.split(",")[0].equals("singles") && !filterBy.equals("numberOfParticipants") && !filterBy.split(",")[0].equals("genre")
                && !filterBy.split(",")[0].equals("date") && !filterBy.split(",")[0].equals("coordinate") && !filterBy.equals("bestAlbum")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        PagedMusicBandList pagedMusicBandList = musicBandService.getMusicBands(perPage, curPage, sortBy, filterBy);
        return new MusicBandDTOList((musicBandMapper.mapMusicBandListToMusicBandDTOList(pagedMusicBandList.getMusicBandList())), pagedMusicBandList.getCount());
    }

    @GetMapping(value = "/{id}")
    public MusicBandDTO getMusicBand(@PathVariable(name = "id") Integer id) {
        MusicBand musicBand = musicBandService.getMusicBandById(id);
//        MusicBandDTOList dto = new MusicBandDTOList(new ArrayList<>(), 1);
//        List<MusicBandDTO> dtoList = dto.getMusicBandList();
//        dtoList.add(musicBandMapper.mapMusicBandToMusicBandDTO(musicBand));

        MusicBandDTO musicBandDTO = musicBandMapper.mapMusicBandToMusicBandDTO(musicBand);

        return musicBandDTO;
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, value = "/{id}")
    public ResponseEntity updateMusicBand(@RequestBody String musicBandDTO) {
        System.out.println("UPDATE");
        MusicBand musicBandToUpdate = musicBandMapper.mapMusicBandDTOToMusicBand(xmlToJava.getSingleMusicBandDTOFromXml(musicBandDTO));
        musicBandService.updateMusicBand(musicBandToUpdate);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createMusicBand(@RequestBody String musicBandDTO) {
        MusicBand musicBandToPersist = musicBandMapper.mapMusicBandDTOToMusicBand(xmlToJava.getSingleMusicBandDTOFromXml(musicBandDTO));
        musicBandService.createMusicBand(musicBandToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMusicBand(@PathVariable(name = "id") Integer id) {
        MusicBand musicBand = musicBandService.getMusicBandById(id);
        if(musicBand == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        musicBandService.deleteMusicBand(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(XmlParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(XmlParseException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


}
