package com.amur.secondservice.dto.dtoList;

import com.amur.secondservice.dto.MusicBandDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class MusicBandDTOList {
    @XmlElementWrapper(name = "musicBands")
    @XmlElement(name = "musicBand")
    private List<MusicBandDTO> musicBandList;
    private long count;
}
