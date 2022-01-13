package service.oriented.architecture.lab2_refactored.dto.dtoList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.dto.AlbumDTO;

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
