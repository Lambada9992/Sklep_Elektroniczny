package pl.sklepelektroniczny.app.outModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sklepelektroniczny.app.model.Parametr;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParametrExt {
    @JsonIgnore
    private Parametr parametr;
    private String parametrNazwa;
    private int parametrId;
    private List<String> wartosc = new ArrayList<>();
    private List<Boolean> active = new ArrayList<>();


}
