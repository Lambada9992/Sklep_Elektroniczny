package pl.sklepelektroniczny.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "slownikstatusow")
public class SlownikStatusow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_status;

    private String status_zamowienia;
}
