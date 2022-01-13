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
import service.oriented.architecture.lab2_refactored.mapper.MusicBandMapper;
import service.oriented.architecture.lab2_refactored.service.MusicBandService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/musicBands", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class MusicBandController {

    private final MusicBandService musicBandService;
    private final MusicBandMapper musicBandMapper;

    public MusicBandController(MusicBandService musicBandService,
                           MusicBandMapper musicBandMapper) {
        this.musicBandService = musicBandService;
        this.musicBandMapper = musicBandMapper;
    }

    @GetMapping
    public MusicBandDTOList getMusicBands(@RequestParam(name = "perPage", required = false) Integer perPage,
                                          @RequestParam(name = "curPage", required = false) Integer curPage,
                                          @RequestParam(name = "sortBy", required = false) String sortBy,
                                          @RequestParam(name = "filterBy", required = false) String filterBy) throws IOException {
        System.out.println("FILTER BY" + filterBy);
        PagedMusicBandList pagedMusicBandList = musicBandService.getMusicBands(perPage, curPage, sortBy, filterBy);
        return new MusicBandDTOList((musicBandMapper.mapMusicBandListToMusicBandDTOList(pagedMusicBandList.getMusicBandList())), pagedMusicBandList.getCount());
    }

    @GetMapping(value = "/{id}")
    public MusicBandDTOList getMusicBand(@PathVariable(name = "id") Integer id) {
        MusicBand musicBand = musicBandService.getMusicBandById(id);
        MusicBandDTOList dto = new MusicBandDTOList(new ArrayList<>(), 1);
        List<MusicBandDTO> dtoList = dto.getMusicBandList();
        dtoList.add(musicBandMapper.mapMusicBandToMusicBandDTO(musicBand));
        return dto;
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, value = "/{id}")
    public ResponseEntity updateMusicBand(@RequestBody MusicBandDTO musicBandDTO) {
        System.out.println("UPDATE");
        MusicBand musicBandToUpdate = musicBandMapper.mapMusicBandDTOToMusicBand(musicBandDTO);
        musicBandService.updateMusicBand(musicBandToUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createMusicBand(@RequestBody MusicBandDTO musicBandDTO) {
        MusicBand musicBandToPersist = musicBandMapper.mapMusicBandDTOToMusicBand(musicBandDTO);
        musicBandService.createMusicBand(musicBandToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMusicBand(@PathVariable(name = "id") Integer id) {
        musicBandService.deleteMusicBand(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
