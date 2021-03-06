package pl.sklepelektroniczny.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "typ")
public class Typ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_typ;
    private String nazwa;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rodzic", referencedColumnName = "id_typ")
    private Typ rodzic;

}
