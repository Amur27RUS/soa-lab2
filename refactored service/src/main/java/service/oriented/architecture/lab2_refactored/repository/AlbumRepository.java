package service.oriented.architecture.lab2_refactored.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.oriented.architecture.lab2_refactored.entity.Album;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Integer> {
    Optional<Album> findById(Integer id);

    List<Album> findAll();

    List<Album> findAllByTracksIsLessThan(int tracks);
}
