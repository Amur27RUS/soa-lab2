package service.oriented.architecture.lab2_refactored.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"x", "y"})
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull(message = "x should not be null")
    private Double x; //Поле не может быть null

    @NotNull(message = "y should not be null")
    private Float y; //Поле не может быть null
}
