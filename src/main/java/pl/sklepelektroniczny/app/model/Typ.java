package pl.sklepelektroniczny.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rodzic",referencedColumnName = "id_typ")
    private Typ rodzic;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "typ",joinColumns = {@JoinColumn(name = "id_typ")},inverseJoinColumns = {@JoinColumn(name = "rodzic")}
//    )
//    private Set<Typ> dzieci = new HashSet<>();
}
