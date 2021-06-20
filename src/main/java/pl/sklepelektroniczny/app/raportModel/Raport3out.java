package pl.sklepelektroniczny.app.raportModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Raport3out {
    private String plec;
    private int iloscZamowien;
    private int iloscProduktow;
    private float wartosc;
    private String Typ;
}
