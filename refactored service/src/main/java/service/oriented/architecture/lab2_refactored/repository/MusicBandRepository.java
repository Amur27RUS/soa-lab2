package service.oriented.architecture.lab2_refactored.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;
import service.oriented.architecture.lab2_refactored.enums.MusicGenre;

import java.util.Optional;

@Repository
public interface MusicBandRepository extends CrudRepository<MusicBand, Integer>, MusicBandRepositoryCustom {
    Optional<MusicBand> findById(Integer id);

    void deleteMusicBandById(Integer id);

    Long countAllByNumberOfParticipantsGreaterThan(Long numberOfParticipants);

    Long countAllByGenreEquals(MusicGenre genre);

}
