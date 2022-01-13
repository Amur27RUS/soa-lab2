package service.oriented.architecture.lab2_refactored.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.oriented.architecture.lab2_refactored.dto.PagedMusicBandList;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;
import service.oriented.architecture.lab2_refactored.enums.MusicGenre;
import service.oriented.architecture.lab2_refactored.repository.MusicBandRepository;
import service.oriented.architecture.lab2_refactored.validation.EntityValidator;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class MusicBandService {

    private final MusicBandRepository musicBandRepository;
    private final EntityValidator entityValidator;

    public MusicBandService(MusicBandRepository musicBandRepository, EntityValidator entityValidator) {
        this.musicBandRepository = musicBandRepository;
        this.entityValidator = entityValidator;
    }

    public MusicBand getMusicBandById(Integer id) {
        return musicBandRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public PagedMusicBandList getMusicBands(Integer perPage, Integer curPage, String sortBy, String filterBy) throws IOException {
        return musicBandRepository.findAll(perPage, curPage, sortBy, filterBy);
    }

    @Transactional
    public void deleteMusicBand(Integer id) {
        musicBandRepository.deleteMusicBandById(id);
    }

    @Transactional
    public void updateMusicBand(MusicBand musicBandToUpdate) {
        MusicBand existingMusicBand = musicBandRepository.findById(musicBandToUpdate.getId())
                .orElseThrow(() -> new NoResultException("Movie with id " + musicBandToUpdate.getId() + " does not exist"));
        musicBandToUpdate.setCreationDate(existingMusicBand.getCreationDate());
        entityValidator.validateMusicBand(musicBandToUpdate);
        musicBandRepository.save(musicBandToUpdate);
    }

    @Transactional
    public void createMusicBand(MusicBand musicBandToPersist) {
        musicBandToPersist.setCreationDate(LocalDate.now());
        entityValidator.validateMusicBand(musicBandToPersist);
        musicBandRepository.save(musicBandToPersist);
    }

    public Long countMusicBandsWithGenreEqualTo(MusicGenre genre) {
        return musicBandRepository.countAllByGenreEquals(genre);
    }

    public Long countMusicBandsWithNumberOfParticipantsGreaterThan(Long numberOfParticipants) {
        return musicBandRepository.countAllByNumberOfParticipantsGreaterThan(numberOfParticipants);
    }
}
