package pl.sklepelektroniczny.app.inModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sklepelektroniczny.app.outModels.ParametrExt;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduktExtFiltr {
    float od_cena = -1;
    float do_cena = -1;
    private String test;
    List<ParametrExt> parametry = new ArrayList<>();

    public void prepare(List<ParametrExt> par) {
        this.parametry = par;
    }

    public void a() {
    }
}
