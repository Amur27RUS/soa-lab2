package com.amur.secondservice.mapper;

import com.amur.secondservice.dto.NominationsDTO;
import com.amur.secondservice.entity.Nominations;
import com.amur.secondservice.exception.EntityIsNotValidException;
import com.amur.secondservice.utils.FieldValidationUtil;

public class NominationsMapper {

    public Nominations mapMusicBandDTOToMusicBand(NominationsDTO nominationsDTO) {
        Nominations nominations = new Nominations();
        nominations.setId(FieldValidationUtil.getIntegerFieldValue(nominationsDTO.getId()));
        nominations.setNominee(nominationsDTO.isNominee());
        nominations.setWinner(nominationsDTO.isWinner());
        nominations.setGenre(FieldValidationUtil.getMusicGenreValue(nominationsDTO.getGenre()));

        return nominations;
    }

    public NominationsDTO mapMusicBandToMusicBandDTO(Nominations nominations) {
        NominationsDTO nominationsDTO = new NominationsDTO();
        nominationsDTO.setId(String.valueOf(nominations.getId()));
        nominationsDTO.setNominee(nominations.isNominee());
        nominationsDTO.setWinner(nominations.isWinner());
        nominationsDTO.setGenre(String.valueOf(nominations.getGenre()));

        return nominationsDTO;
    }
}
