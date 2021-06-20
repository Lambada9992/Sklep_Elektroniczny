package pl.sklepelektroniczny.app.raportModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Raport1 {
    private String nazwaProduktu;
    private int id_produkt;
    private float cena;
    private int ilosc;
    private float suma;
    private int rok;
    private int miesiac;
    private int kwartal;

    private String nazwaProducent;
    private String typ;
}
