package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.Typ;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TypTree {
    @JsonIgnore
    private Typ typ;
    private int id_typ;
    @JsonProperty("typ")
    private String typNazwa;
    private List<TypTree> podTypy = new ArrayList<>();

    public TypTree(Typ typ) {
        this.typ = typ;
        this.id_typ = typ.getId_typ();
        this.typNazwa = typ.getNazwa();
    }
}
