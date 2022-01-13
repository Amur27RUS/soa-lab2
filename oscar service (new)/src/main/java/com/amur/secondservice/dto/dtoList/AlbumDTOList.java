package com.amur.secondservice.dto.dtoList;

import com.amur.secondservice.dto.AlbumDTO;
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
public class AlbumDTOList {
    @XmlElementWrapper(name = "albums")
    @XmlElement(name = "album")
    private List<AlbumDTO> albumsList;
}
