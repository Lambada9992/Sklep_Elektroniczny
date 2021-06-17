package pl.sklepelektroniczny.app.outModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KoszykExt {

    private float suma = 0;
    private List<KoszykPozycja> pozycje;

    public KoszykExt(List<KoszykPozycja> pozycje) {
        this.pozycje = pozycje;
        //suma += (float)pozycje.stream().mapToDouble(pozycja -> pozycja.getCena()).sum();
        pozycje.forEach(pozycja -> {
            if (pozycja.getCena() != -1f) {
                suma += pozycja.getCena() * pozycja.getIlosc();
            }
        });
    }
}
