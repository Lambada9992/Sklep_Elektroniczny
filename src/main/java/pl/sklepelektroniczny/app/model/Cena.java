package pl.sklepelektroniczny.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cena")
public class Cena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cena;

    private int id_produkt;
    private float cena;
    private Timestamp od_daty;
    private Timestamp do_daty;
}
