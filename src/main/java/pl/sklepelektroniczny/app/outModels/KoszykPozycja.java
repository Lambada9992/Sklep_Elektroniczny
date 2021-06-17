package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.Koszyk;

@Getter
@Setter
public class KoszykPozycja {
    @JsonIgnore
    private ProduktExt produktExt;
    @JsonIgnore
    private Koszyk koszyk;

    private int id_produkt;
    private String nazwa;
    private float cena;
    private int ilosc;

    public KoszykPozycja(ProduktExt produktExt, Koszyk koszyk) {
        this.produktExt = produktExt;
        this.koszyk = koszyk;
        this.id_produkt = produktExt.getProdukt().getId_produkt();
        this.nazwa = produktExt.getNazwa();
        this.cena = produktExt.getCena();
        this.ilosc = koszyk.getIlosc();
    }
}
