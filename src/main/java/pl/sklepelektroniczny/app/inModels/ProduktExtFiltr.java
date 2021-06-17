package pl.sklepelektroniczny.app.inModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.Typ;
import pl.sklepelektroniczny.app.outModels.ParametrFix;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduktExtFiltr {
    Typ typ = null;
    float od_cena = -1;
    float do_cena = -1;
    List<ParametrFix> parametry = new ArrayList<>();
}
