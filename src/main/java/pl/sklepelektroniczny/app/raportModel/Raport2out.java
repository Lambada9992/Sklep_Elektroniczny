package pl.sklepelektroniczny.app.raportModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Raport2out {
    private int id_zamowienia;
    private int ilosc;
    private String data_zamowienia;
    private float cena;
    private int id_uzytkownik;
    private String login;
    private String status;
}
