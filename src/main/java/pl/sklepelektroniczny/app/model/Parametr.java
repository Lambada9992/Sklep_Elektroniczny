package pl.sklepelektroniczny.app.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parametr")
public class Parametr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_parametr;

    private String nazwa;
    private Boolean slownikowany;
}
