package pl.sklepelektroniczny.app.inModels;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DaneZamowienia {
    private String miasto;
    private String ulica;
    private String kodPocztowy;
}
