package pl.sklepelektroniczny.app.raportModel;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Raport2 {
    private int id_zamowienia;
    private int ilosc;
    private Timestamp data_zamowienia;
    private float cena;
    private int id_uzytkownik;
    private String login;
    private int id_status;
    private String status;

    private String nazwa;
    private String typ;
}
