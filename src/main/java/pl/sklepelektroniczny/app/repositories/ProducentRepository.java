package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Producent;

@Repository
public interface ProducentRepository extends JpaRepository<Producent,Integer> {
}
