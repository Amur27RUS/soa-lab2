package service.oriented.architecture.lab2_refactored.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ALBUM")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @DecimalMin(value = "0", inclusive = false, message = "tracks must be > 0")
    private int tracks; //Значение должно быть больше 0

    @DecimalMin(value = "0", inclusive = false, message = "length must be > 0")
    private Integer length; //Поле не может быть null, значение должно быть больше 0

    @NotNull(message = "name should not be null")
    private String name; //Поле не может быть null

    @DecimalMin(value = "0", inclusive = false, message = "length must be > 0")
    private double sales; //Значение должно быть больше 0
}
