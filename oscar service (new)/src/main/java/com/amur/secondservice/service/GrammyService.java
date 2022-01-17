package com.amur.secondservice.service;

import com.amur.secondservice.client.RestClient;
import com.amur.secondservice.dto.MusicBandDTO;
import com.amur.secondservice.dto.dtoList.MusicBandDTOList;
import com.amur.secondservice.enums.MusicGenre;
import com.amur.secondservice.exception.BadRequestException;
import com.amur.secondservice.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;

@Service
public class GrammyService {

    private final RestClient restClient;

    public GrammyService(RestClient restClient) {
        this.restClient = restClient;
    }

    public void nominateBand(Integer bandId, String genre) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        MusicBandDTOList musicBandDTOList = restClient.getMusicBandById(bandId);
        List<MusicBandDTO> musicBandDTOArr = musicBandDTOList.getMusicBandList();
        System.out.println(musicBandDTOArr.get(0).getId());

        if(musicBandDTOArr.get(0) == null){
            throw new NotFoundException("Music band with id = " + bandId + " not found");
        }
        if(!Objects.equals(musicBandDTOArr.get(0).getGenre().toString(), genre)) {
            throw new BadRequestException("Music band genre mismatch");
        }
        MusicBandDTO musicBandDTO = musicBandDTOList.getMusicBandList().get(0);
        musicBandDTO.setNominee(true);
        restClient.updateMusicBand(musicBandDTO);
    }

    public void rewardBand(Integer bandId, MusicGenre genre) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        MusicBandDTOList musicBandDTOList = restClient.getMusicBandById(bandId);
        List<MusicBandDTO> musicBandDTOArr = musicBandDTOList.getMusicBandList();

        if(musicBandDTOArr.get(0) == null){
            throw new NotFoundException("Music band with id = " + bandId + " not found");
        }
        if(!Objects.equals(musicBandDTOArr.get(0).getGenre(), genre.toString())){
            throw new BadRequestException("Music band genre mismatch");
        }
        if(!musicBandDTOArr.get(0).isNominee()){
            throw new BadRequestException("Music band is not a nominee!");
        }

        MusicBandDTO musicBandDTO = musicBandDTOList.getMusicBandList().get(0);
        musicBandDTO.setWinner(true);
        restClient.updateMusicBand(musicBandDTO);
    }
}
