package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sklepelektroniczny.app.inModels.ProduktExtFiltr;
import pl.sklepelektroniczny.app.model.Produkt;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduktExt {

    @JsonIgnore
    private Produkt produkt;

    private int id_produkt;
    private String nazwa;
    private String opis;
    private String producent;
    private String typ;
    private Float cena;
    private List<ParametrFix> parametry;

    public ProduktExt(Produkt produkt) {
        this.produkt = produkt;
        this.id_produkt = produkt.getId_produkt();
        this.nazwa = produkt.getNazwa();
        this.opis = produkt.getOpis();
        this.typ = produkt.getTyp().getNazwa();
        this.producent = produkt.getProducent().getNazwa();
    }

    public boolean checkFiltr(ProduktExtFiltr pef) {

        if (pef == null) return true;
//        if (pef.getTyp() != null) {
//            if (pef.getTyp().getId_typ() != produkt.getTyp().getId_typ()) return false;
//        }
        if (pef.getOd_cena() != -1) {
            if (cena < pef.getOd_cena()) return false;
        }
        if (pef.getDo_cena() != -1) {
            if (cena > pef.getDo_cena()) return false;
        }

        List<Integer> id_param = new ArrayList<>();
        List<Boolean> satisfied = new ArrayList<>();
//TODO dO naprawy

//        pef.getParametry().forEach(pf -> {
//            int index;
//            if (id_param.contains(pf.getId_parametr())) {
//                index = id_param.indexOf(pf.getId_parametr());
//            } else {
//                id_param.add(pf.getId_parametr());
//                index = id_param.indexOf(pf.getId_parametr());
//                satisfied.add(false);
//            }
//
//            if (!satisfied.get(index)) {
//                this.parametry.forEach(pp -> {
//                    if (pp.getId_parametr() == pf.getId_parametr()) {
//                        if (pp.getWartosc().equals(pf.getWartosc())) {
//                            satisfied.set(index, true);
//                        }
//                    }
//                });
//            }
//        });

        for (Boolean var : satisfied) {
            if (var == false) return false;
        }

        return true;
    }
}
