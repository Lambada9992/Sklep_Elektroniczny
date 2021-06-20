package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Cena;

import java.util.Optional;

@Repository
public interface CenaRepository extends JpaRepository<Cena, Integer> {
    @Query(
            value = "SELECT * FROM cena c WHERE c.id_produkt = ?1 AND c.do_daty IS NULL ORDER BY c.od_daty DESC LIMIT 1",
            nativeQuery = true)
    Optional<Cena> findAktCena(int id_produktu);

}
