package com.amur.secondservice.service;


import com.amur.secondservice.client.RestClient;
import com.amur.secondservice.dto.MusicBandDTO;
import com.amur.secondservice.entity.Nominations;
import com.amur.secondservice.enums.MusicGenre;
import com.amur.secondservice.exception.BadRequestException;
import com.amur.secondservice.exception.NotFoundException;
import com.amur.secondservice.repository.NominationsRepository;
import com.amur.secondservice.utils.FieldValidationUtil;
import com.amur.secondservice.validation.EntityValidator;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GrammyService {

    private final RestClient restClient;
    private final NominationsRepository nominationsRepository;
    private final EntityValidator entityValidator;

    public GrammyService(RestClient restClient, NominationsRepository nominationsRepository, EntityValidator entityValidator) {
        this.restClient = restClient;
        this.nominationsRepository = nominationsRepository;
        this.entityValidator = entityValidator;
    }

    public void nominateBand(Integer bandId, String genre) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        System.out.println("НАЧИНАЮ ПОЛУЧЕНИЕ ГРУППЫ...");
        MusicBandDTO musicBandDTO = restClient.getMusicBandById(bandId);
        System.out.println(musicBandDTO.getName() + " ПОЛУЧЕННАЯ ГРУППА");

        if(musicBandDTO == null){
            throw new BadRequestException("Music band with id = " + bandId + " not found");
        }
        if(!Objects.equals(musicBandDTO.getGenre().toString(), genre)) {
            throw new BadRequestException("Music band genre mismatch");
        }

        Nominations nominations = new Nominations();
        nominations.setId(bandId);
        nominations.setNominee(true);
        nominations.setGenre(FieldValidationUtil.getMusicGenreValue(genre));

        if(!nominationsRepository.findById(bandId).isPresent()){
            createNominations(nominations);
        }

        updateNominations(nominations);
    }

    public void rewardBand(Integer bandId, String genre) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        MusicBandDTO musicBandDTO = restClient.getMusicBandById(bandId);
        System.out.println(musicBandDTO.getName() + " ПОЛУЧЕННАЯ ГРУППА");
        MusicGenre musicGenre = MusicGenre.PSYCHEDELIC_ROCK;

        if(musicBandDTO == null){
            throw new NotFoundException("Music band with id = " + bandId + " not found");
        }
        if(!Objects.equals(musicBandDTO.getGenre(), genre.toString())){
            throw new BadRequestException("Music band genre mismatch");
        }

        Nominations nominations = nominationsRepository.getNominationsByIdAndNomineeIsTrue(bandId);

        if(!nominationsRepository.findById(bandId).isPresent() || nominations == null){
            throw new BadRequestException("Music band is not nominated!");
        }
        if(genre.equals("BRIT_POP")){
            musicGenre = MusicGenre.BRIT_POP;
        }else if(genre.equals("POST_ROCK")){
            musicGenre= MusicGenre.POST_ROCK;
        }else if(genre.equals("PROGRESSIVE_ROCK")){
            musicGenre= MusicGenre.PROGRESSIVE_ROCK;
        }else if(genre.equals("PSYCHEDELIC_ROCK")){
            musicGenre= MusicGenre.PSYCHEDELIC_ROCK;
        }
        List<Nominations> nominationsList = nominationsRepository.getNominationsByGenreAndWinnerIsTrue(musicGenre);

        if(!nominationsList.isEmpty()){
            throw new BadRequestException("There's at least one winner in selected genre");
        }


        Nominations finalNominations = new Nominations();
        finalNominations.setId(bandId);
        finalNominations.setNominee(true);
        finalNominations.setWinner(true);
        finalNominations.setGenre(musicGenre);

        updateNominations(finalNominations);
    }

    @Transactional
    public void updateNominations(Nominations nominations) {
        nominationsRepository.findById(nominations.getId())
                .orElseThrow(() -> new NoResultException(" Nomination with id " + nominations.getId() + " does not exist"));
        entityValidator.validateNominations(nominations);
        nominationsRepository.save(nominations);
    }

    @Transactional
    public void createNominations(Nominations nominations) {
        entityValidator.validateNominations(nominations);
        nominationsRepository.save(nominations);
    }


}
