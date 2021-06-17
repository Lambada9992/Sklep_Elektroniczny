package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.ProduktParametr;

@Getter
@Setter
@NoArgsConstructor
public class ParametrFix {
    @JsonIgnore
    ProduktParametr produktParametr = null;
    private String parametr;
    private int id_parametr;
    private String wartosc;

    public ParametrFix(ProduktParametr produktParametr) {
        this.produktParametr = produktParametr;
        this.parametr = produktParametr.getParametr().getNazwa();
        this.id_parametr = produktParametr.getParametr().getId_parametr();
        if (produktParametr.getParametr().getSlownikowany() == false) {
            this.wartosc = produktParametr.getWartosc();
        } else {
            this.wartosc = produktParametr.getSlownikParametrow().getParametr_wartosc();
        }
    }

    public ParametrFix(String parametr, int id_parametr, String wartosc) {
        this.parametr = parametr;
        this.id_parametr = id_parametr;
        this.wartosc = wartosc;
    }
}
