package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Pracownik;

@Repository
public interface PracownikRepository extends JpaRepository<Pracownik, Integer> {

}
