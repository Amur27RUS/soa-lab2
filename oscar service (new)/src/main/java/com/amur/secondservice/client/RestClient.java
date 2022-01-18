package com.amur.secondservice.client;

import com.amur.secondservice.dto.MusicBandDTO;
import com.amur.secondservice.dto.dtoList.MusicBandDTOList;
import com.amur.secondservice.utils.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class RestClient {

    @Value("${host.path}")
    String REST_URI;

    final RestTemplateConfig restTemplateConfig;

    public RestClient(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
    }

    public MusicBandDTO getMusicBandById(Integer id) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        return restTemplateConfig.restTemplate().getForObject(REST_URI + "/" + id, MusicBandDTO.class);
    }

    public void updateMusicBand(MusicBandDTO musicBandDTO) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        System.out.println(musicBandDTO.isNominee());

        String xml = "<musicBandDTO>" +
                "<id>" +musicBandDTO.getId()+ "</id>" +
                "<name>" +musicBandDTO.getName()+ "</name>" +
                "<creationDate>" +musicBandDTO.getCreationDate()+ "</creationDate>" +
                "<description>" +musicBandDTO.getDescription()+ "</description>" +
                "<numberOfParticipants>" +musicBandDTO.getNumberOfParticipants()+ "</numberOfParticipants>" +
                "<genre>" +musicBandDTO.getGenre()+ "</genre>" +
                "<singlesCount>" +musicBandDTO.getSinglesCount()+ "</singlesCount>" +
                "<coordinates><id>" +musicBandDTO.getCoordinates().getId()+ "</id></coordinates>" +
                "<bestAlbum>" +
                "<id>" +musicBandDTO.getBestAlbum().getId()+ "</id>" +
                "</bestAlbum>" +
                "<nominee>" + musicBandDTO.isNominee() + "</nominee>" +
                "<winner>" + musicBandDTO.isWinner() + "</winner>" + "</musicBandDTO>";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/xml; charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(xml, headers);
        restTemplateConfig.restTemplate().put(REST_URI + "/" + musicBandDTO.getId(), entity, MusicBandDTO.class);

    }
}
