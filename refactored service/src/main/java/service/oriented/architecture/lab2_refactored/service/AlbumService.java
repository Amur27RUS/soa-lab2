package service.oriented.architecture.lab2_refactored.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.repository.AlbumRepository;
import service.oriented.architecture.lab2_refactored.validation.EntityValidator;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final EntityValidator entityValidator;

    public AlbumService(AlbumRepository albumRepository, EntityValidator entityValidator) {
        this.albumRepository = albumRepository;
        this.entityValidator = entityValidator;
    }

    public Album getAlbumById(Integer id) {
        return albumRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public List<Album> getAlbum() {
        return albumRepository.findAll();
    }

    @Transactional
    public void updateAlbum(Album albumToUpdate) {
        albumRepository.findById(albumToUpdate.getId())
                .orElseThrow(() -> new NoResultException(" Album with id " + albumToUpdate.getId() + " does not exist"));
        entityValidator.validateAlbum(albumToUpdate);
        albumRepository.save(albumToUpdate);
    }

    @Transactional
    public void createAlbum(Album albumToPersist) {
        entityValidator.validateAlbum(albumToPersist);
        albumRepository.save(albumToPersist);
    }

    public List<Album> getAlbumWithTracksLessThan(int tracks) { return albumRepository.findAllByTracksIsLessThan(tracks);}
}
