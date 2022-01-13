package com.amur.secondservice.client;

import com.amur.secondservice.dto.MusicBandDTO;
import com.amur.secondservice.dto.dtoList.MusicBandDTOList;
import com.amur.secondservice.utils.RestTemplateConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class RestClient {
    private static final String REST_URI = "https://localhost:8881/musicBands";

    final RestTemplateConfig restTemplateConfig;

    public RestClient(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
    }

    public MusicBandDTOList getMusicBandById(Integer id) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        return restTemplateConfig.restTemplate().getForObject(REST_URI + "/" + id, MusicBandDTOList.class);
    }

    public void updateMusicBand(MusicBandDTO musicBandDTO) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        System.out.println(musicBandDTO);
        restTemplateConfig.restTemplate().put(REST_URI + "/" + musicBandDTO.getId(), "<musicBandDTO>" +
                "<id>" +musicBandDTO.getId()+ "</id>" +
                "<name>" +musicBandDTO.getName()+ "</name>" +
                "<creationDate>" +musicBandDTO.getCreationDate()+ "</creationDate>" +
                "<description>" +musicBandDTO.getDescription()+ "</description>" +
                "<numberOfParticipants>" +musicBandDTO.getNumberOfParticipants()+ "</numberOfParticipants>" +
                "<genre>" +musicBandDTO.getGenre()+ "</genre>" +
                "<singlesCount>" +musicBandDTO.getSinglesCount()+ "</singlesCount>" +
                "<coordinates><id>" +musicBandDTO.getCoordinates()+ "</id></coordinates>" +
                "<bestAlbum>" +
                "<id>" +musicBandDTO.getBestAlbum()+ "</id>" +
                "</bestAlbum>" +
                "<nominee>" + musicBandDTO.isNominee() + "</nominee>" +
                "<winner>" + musicBandDTO.isWinner() + "</winner>" + "</musicBandDTO>");
    }
}
