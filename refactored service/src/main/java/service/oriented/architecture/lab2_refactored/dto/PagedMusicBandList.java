package service.oriented.architecture.lab2_refactored.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagedMusicBandList {
    private List<MusicBand> musicBandList;
    private Long count;
}
