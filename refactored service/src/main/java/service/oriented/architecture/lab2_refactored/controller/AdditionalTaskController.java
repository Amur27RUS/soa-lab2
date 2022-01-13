package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.oriented.architecture.lab2_refactored.dto.CountDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.AlbumDTOList;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.enums.MusicGenre;
import service.oriented.architecture.lab2_refactored.mapper.AlbumMapper;
import service.oriented.architecture.lab2_refactored.service.AlbumService;
import service.oriented.architecture.lab2_refactored.service.MusicBandService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
@Validated
public class AdditionalTaskController {

    private final MusicBandService musicBandService;
    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    public AdditionalTaskController(MusicBandService musicBandService,
                                    AlbumService albumService,
                                    AlbumMapper albumMapper) {
        this.musicBandService = musicBandService;
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping(value = "/musicBands/getMusicBandsByGenre")
    public CountDTO getCountWithGenreEqualsTo(@RequestParam(name = "genre") MusicGenre genre) {
        Long countResult = musicBandService.countMusicBandsWithGenreEqualTo(genre);
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(countResult);
        return countDTO;
    }

    @GetMapping(value = "/musicBands/getMusicBandsByNOP")
    public CountDTO getCountWithNumberOfParticipantsGreaterThan(@RequestParam(name = "NoP") Long numberOfParticipants) {
        Long countResult = musicBandService.countMusicBandsWithNumberOfParticipantsGreaterThan(numberOfParticipants);
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(countResult);
        return countDTO;
    }

    @GetMapping(value = "/albums/getAlbumsByTracks")
    public AlbumDTOList getAlbumsListLessThan(@RequestParam(name = "tracks") Integer tracks) {
        List<Album> albumList = albumService.getAlbumWithTracksLessThan(tracks);
        AlbumDTOList dto = new AlbumDTOList(new ArrayList<>());
        dto.setAlbumsList(albumMapper.mapAlbumListToAlbumDTOList(albumList));
        return dto;
    }
}
