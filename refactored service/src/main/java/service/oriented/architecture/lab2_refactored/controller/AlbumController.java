package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.oriented.architecture.lab2_refactored.dto.AlbumDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.AlbumDTOList;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.mapper.AlbumMapper;
import service.oriented.architecture.lab2_refactored.service.AlbumService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/albums", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    public AlbumController(AlbumService albumService,
                            AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping(value = "/{id}")
    public AlbumDTOList getAlbum(@PathVariable(name = "id") Integer id) {
        Album album = albumService.getAlbumById(id);
        AlbumDTOList dto = new AlbumDTOList(new ArrayList<>());
        List<AlbumDTO> dtoList = dto.getAlbumsList();
        dtoList.add(albumMapper.mapAlbumToAlbumDTO(album));
        return dto;
    }

    @GetMapping
    public AlbumDTOList getAlbums() {
        List<Album> albumList = albumService.getAlbum();
        AlbumDTOList dto = new AlbumDTOList(new ArrayList<>());
        dto.setAlbumsList(albumMapper.mapAlbumListToAlbumDTOList(albumList));
        return dto;
    }


    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity createAlbum(@RequestBody AlbumDTOList albumDTOList) {
        Album albumToPersist = albumMapper.mapAlbumDTOToAlbum(albumDTOList.getAlbumsList().get(0));
        albumService.createAlbum(albumToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, value = "/{id}")
    public ResponseEntity updateAlbum(@RequestBody AlbumDTO albumDTO) {
        Album albumToUpdate = albumMapper.mapAlbumDTOToAlbum(albumDTO);
        albumService.updateAlbum(albumToUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }
}
