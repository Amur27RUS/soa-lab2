package service.oriented.architecture.lab2_refactored.mapper;



import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.dto.MusicBandDTO;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;
import service.oriented.architecture.lab2_refactored.repository.AlbumRepository;
import service.oriented.architecture.lab2_refactored.repository.CoordinatesRepository;
import service.oriented.architecture.lab2_refactored.utils.FieldValidationUtil;

import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicBandMapper {
    private CoordinatesMapper coordinatesMapper;
    private AlbumMapper albumMapper;
    private CoordinatesRepository coordinatesRepository;
    private AlbumRepository albumRepository;

    public MusicBandMapper(CoordinatesMapper coordinatesMapper, AlbumMapper albumMapper, CoordinatesRepository coordinatesRepository, AlbumRepository albumRepository) {
        this.coordinatesMapper = coordinatesMapper;
        this.albumMapper = albumMapper;
        this.coordinatesRepository = coordinatesRepository;
        this.albumRepository = albumRepository;
    }

    public MusicBand mapMusicBandDTOToMusicBand(MusicBandDTO musicBandDTO) {
            MusicBand musicBand = new MusicBand();
            System.out.println(musicBandDTO.getName());
            musicBand.setId(FieldValidationUtil.getIntegerFieldValue(musicBandDTO.getId()));
            musicBand.setCoordinates(coordinatesMapper.mapCoordinatesDTOToCoordinates(musicBandDTO.getCoordinates()));
            if (musicBand.getCoordinates().getId() == null)
                throw new EntityIsNotValidException("coordinates must not be null");
            if (musicBand.getCoordinates() != null && !coordinatesRepository.existsById(musicBand.getCoordinates().getId()))
                throw new EntityIsNotValidException("coordinates with id = " + musicBand.getCoordinates().getId() + " does not exist");
            musicBand.setNumberOfParticipants(FieldValidationUtil.getLongFieldValue(musicBandDTO.getNumberOfParticipants()));
            musicBand.setGenre(FieldValidationUtil.getMusicGenreValue(musicBandDTO.getGenre()));
            musicBand.setName(FieldValidationUtil.getStringValue(musicBandDTO.getName()));
            musicBand.setDescription(FieldValidationUtil.getStringValue(musicBandDTO.getDescription()));
            musicBand.setSinglesCount(FieldValidationUtil.getLongFieldValue(musicBandDTO.getSinglesCount()));
            musicBand.setNominee(musicBandDTO.isNominee());
            musicBand.setWinner(musicBandDTO.isWinner());
            Album bestAlbum;
            if (!musicBandDTO.getBestAlbum().getId().equals("")) {
                bestAlbum = albumRepository.findById(Integer.parseInt(musicBandDTO.getBestAlbum().getId())).get();
            } else {
                throw new EntityIsNotValidException("bestAlbum must not be null");
            }
        musicBand.setBestAlbum(bestAlbum);
        return musicBand;
    }

    public MusicBandDTO mapMusicBandToMusicBandDTO(MusicBand musicBand) {
        MusicBandDTO musicBandDTO = new MusicBandDTO();
        musicBandDTO.setId(String.valueOf(musicBand.getId()));
        musicBandDTO.setName(musicBand.getName());
        musicBandDTO.setCoordinates(coordinatesMapper.mapCoordinatesToCoordinatesDTO(musicBand.getCoordinates()));
        musicBandDTO.setNumberOfParticipants(String.valueOf(musicBand.getNumberOfParticipants()));
        musicBandDTO.setCreationDate(String.valueOf(musicBand.getCreationDate()));
        musicBandDTO.setBestAlbum(albumMapper.mapAlbumToAlbumDTO(musicBand.getBestAlbum()));
        musicBandDTO.setDescription(String.valueOf(musicBand.getDescription()));
        musicBandDTO.setGenre(String.valueOf(musicBand.getGenre()));
        musicBandDTO.setSinglesCount(String.valueOf(musicBand.getSinglesCount()));
        musicBandDTO.setNominee(musicBand.isNominee());
        musicBandDTO.setWinner(musicBand.isWinner());
        return musicBandDTO;
    }

    public List<MusicBandDTO> mapMusicBandListToMusicBandDTOList(List<MusicBand> musicBandList) {
        ArrayList<MusicBandDTO> musicBandDTOArrayList = new ArrayList<>();
        for (MusicBand musicBand : musicBandList) {
            musicBandDTOArrayList.add(mapMusicBandToMusicBandDTO(musicBand));
        }
        return musicBandDTOArrayList;
    }


}
