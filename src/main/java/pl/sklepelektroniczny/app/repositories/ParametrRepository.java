package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Parametr;

@Repository
public interface ParametrRepository extends JpaRepository<Parametr, Integer> {

}
