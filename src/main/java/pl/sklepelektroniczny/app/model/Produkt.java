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
@Entity(name = "produkt")
public class Produkt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_produkt;
    private String nazwa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_producent", referencedColumnName = "id_producent")
    private Producent producent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_typ",referencedColumnName = "id_typ")
    private Typ typ;

    private int ilosc;
    private String opis;




}
