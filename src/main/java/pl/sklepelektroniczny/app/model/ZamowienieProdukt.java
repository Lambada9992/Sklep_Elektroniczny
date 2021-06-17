package pl.sklepelektroniczny.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "zamowienie_produkt")
public class ZamowienieProdukt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_zamowienie_produkt;
    private int id_zamowienia;
    private int id_produkt;
    private int ilosc;
    private float cena;
}
