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
@Table(name = "produkt_parametr")
public class ProduktParametr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_produkt_parametr;

    private int id_produkt;
    @ManyToOne
    @JoinColumn(name = "id_parametr")
    private Parametr parametr;
    private String wartosc;
    @ManyToOne
    @JoinColumn(name = "id_slownik_parametrow")
    private SlownikParametrow slownikParametrow;
}
