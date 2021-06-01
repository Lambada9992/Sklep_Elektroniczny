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
@Table(name = "uzytkownik")
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Auto
    private int id_uzytkownik;
    private String login;
    private String haslo;
    private String imie;
    private String nazwisko;
    private String email;
    private String kod_pocztowy;
    private String miasto;
    private String ulica;
    private String plec;

}
