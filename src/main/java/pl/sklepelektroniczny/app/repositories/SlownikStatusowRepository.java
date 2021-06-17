package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.SlownikStatusow;

import java.util.Optional;

@Repository
public interface SlownikStatusowRepository extends JpaRepository<SlownikStatusow, Integer> {

    @Query(value = "SELECT * FROM slownikstatusow ss WHERE ss.status_zamowienia = ?1", nativeQuery = true)
    Optional<SlownikStatusow> findByNazwaStatusu(String nazwa);
}
