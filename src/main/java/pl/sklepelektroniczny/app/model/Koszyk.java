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
@Entity(name = "koszyk")
public class Koszyk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_koszyk;
    private int id_uzytkownik;
    private int id_produkt;
    private int ilosc;
}
