package com.amur.secondservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class MusicBandDTO {
    private String id;
    private String name;
    private CoordinatesDTO coordinates;
    private String creationDate;
    private String singlesCount;
    private String numberOfParticipants;
    private String description;
    private String genre;
    private AlbumDTO bestAlbum;
    private boolean nominee;
    private boolean winner;
}
