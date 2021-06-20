package pl.sklepelektroniczny.app.raportModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Raport1out {
    private int id_produkt;
    private String nazwaProduktu;
    private String typ;
    private String nazwaProducent;
    private float cena;
    private int ilosc;
    private float suma;
    private String rok;
    private String miesiac_kwartal;
}
