package com.amur.secondservice.repository;

import com.amur.secondservice.entity.Nominations;
import com.amur.secondservice.enums.MusicGenre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NominationsRepository extends CrudRepository<Nominations, Integer> {
    List<Nominations> findAll();

    List<Nominations> getNominationsByGenreAndWinnerIsTrue(MusicGenre genre);

    Nominations getNominationsByIdAndNomineeIsTrue(Integer id);
}
