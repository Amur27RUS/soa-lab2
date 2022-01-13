package service.oriented.architecture.lab2_refactored.repository;

import service.oriented.architecture.lab2_refactored.dto.PagedMusicBandList;

import java.io.IOException;

public interface MusicBandRepositoryCustom {
    PagedMusicBandList findAll(Integer perPage, Integer curPage, String sortBy, String filterBy) throws IOException;
}
