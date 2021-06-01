package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sklepelektroniczny.app.model.Typ;

public interface TypRepository extends JpaRepository<Typ,Integer> {
}
