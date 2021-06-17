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
    String miasto;
    String ulica;
    String kodPocztowy;
}
