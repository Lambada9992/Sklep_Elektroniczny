package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sklepelektroniczny.app.model.Producent;

public interface ProducentRepository extends JpaRepository<Producent,Integer> {
}
