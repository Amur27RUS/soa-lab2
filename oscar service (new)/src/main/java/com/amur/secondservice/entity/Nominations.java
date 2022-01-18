package com.amur.secondservice.entity;

import com.amur.secondservice.enums.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Entity
@Table(name = "NOMINATIONS")
@NamedQuery(name = "Entity.Nominations.getAll", query = "SELECT n FROM Nominations n")
public class Nominations {
    @Id
    private Integer id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(name = "nominee")
    private boolean nominee;

    @Column(name = "winner")
    private boolean winner;

    @Enumerated(EnumType.STRING)
    private MusicGenre genre;
}
