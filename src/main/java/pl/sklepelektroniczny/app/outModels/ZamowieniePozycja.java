package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.ZamowienieProdukt;

@Getter
@Setter
public class ZamowieniePozycja {

    @JsonIgnore
    ProduktExt produktExt;
    @JsonIgnore
    ZamowienieProdukt zamowienieProdukt;

    private int id_produkt;
    private String nazwa;
    private int ilosc;
    private float cena;

    public ZamowieniePozycja(ProduktExt produktExt, ZamowienieProdukt zamowienieProdukt) {
        this.produktExt = produktExt;
        this.zamowienieProdukt = zamowienieProdukt;
        this.id_produkt = produktExt.getProdukt().getId_produkt();
        this.nazwa = produktExt.getNazwa();
        this.ilosc = zamowienieProdukt.getIlosc();
        this.cena = zamowienieProdukt.getCena();
    }
}
