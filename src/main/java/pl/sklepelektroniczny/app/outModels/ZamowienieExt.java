package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.Zamowienie;

import java.util.List;

@Getter
@Setter
public class ZamowienieExt {
    @JsonIgnore
    Zamowienie zamowienie;

    private int id_zamowienia;
    private List<ZamowieniePozycja> pozycje;
    private float suma = 0;
    private String status;

    public ZamowienieExt(Zamowienie zamowienie) {
        this.zamowienie = zamowienie;
        this.id_zamowienia = zamowienie.getId_zamowienia();
    }
}
