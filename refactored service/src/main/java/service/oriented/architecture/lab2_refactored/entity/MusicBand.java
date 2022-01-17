package service.oriented.architecture.lab2_refactored.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.enums.MusicGenre;
import service.oriented.architecture.lab2_refactored.xml.LocalDateAdapter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Entity
@Table(name = "MUSICBAND")
@NamedQuery(name = "Entity.MusicBand.getAll", query = "SELECT mb FROM MusicBand mb")
public class MusicBand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = "name should not be null")
    private String name;//Поле не может быть null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinate_id")
    @XmlElement(name = "coordinates")
    private Coordinates coordinates;//Поле не может быть null

    @Column(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @NotNull(message = "creation date should not be null")
    private LocalDate creationDate;//Значение этого поля должно генерироваться автоматически

    @Column(name = "singles")
    @DecimalMin(value = "0", inclusive = false, message = "singles count must be > 0")
    private Long singlesCount; //Значение поля должно быть больше 0, Поле может быть null

    @DecimalMin(value = "0", inclusive = false, message = "numberOfParticipants must be > 0")
    private Long numberOfParticipants;//Значение поля должно быть больше 0

    private String description;

    @Enumerated(EnumType.STRING)
    private MusicGenre genre; //Поле может быть null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    @XmlElement(name = "bestAlbum")
    private Album bestAlbum; //Поле не может быть null

    @Column(name = "nominee")
    private boolean nominee;

    @Column(name = "winner")
    private boolean winner;
}
