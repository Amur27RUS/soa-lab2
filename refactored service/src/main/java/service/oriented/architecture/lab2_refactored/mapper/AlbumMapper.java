package service.oriented.architecture.lab2_refactored.mapper;

import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.dto.AlbumDTO;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.utils.FieldValidationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumMapper {

    public Album mapAlbumDTOToAlbum(AlbumDTO albumDTO) {
        Album album = new Album();
        album.setId(FieldValidationUtil.getIntegerFieldValue(albumDTO.getId()));
        album.setLength(FieldValidationUtil.getIntegerFieldValue(albumDTO.getLength()));
        album.setName(FieldValidationUtil.getStringValue(albumDTO.getName()));
        if(albumDTO.getSales() != null){
            album.setSales(FieldValidationUtil.getDoubleFieldValue(albumDTO.getSales()));
        }
        if(albumDTO.getTracks() != null){
            album.setTracks(FieldValidationUtil.getIntegerFieldValue(albumDTO.getTracks()));

        }

        return album;
    }

    public AlbumDTO mapAlbumToAlbumDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(String.valueOf(album.getId()));
        albumDTO.setName(album.getName());
        albumDTO.setLength(String.valueOf(album.getLength()));
        albumDTO.setTracks(String.valueOf(album.getTracks()));
        albumDTO.setSales(String.valueOf(album.getSales()));
        return albumDTO;
    }

    public List<AlbumDTO> mapAlbumListToAlbumDTOList(List<Album> albumsList) {
        ArrayList<AlbumDTO> albumDTOArrayList = new ArrayList<>();
        for (Album album : albumsList) {
            albumDTOArrayList.add(mapAlbumToAlbumDTO(album));
        }
        return albumDTOArrayList;
    }
}
