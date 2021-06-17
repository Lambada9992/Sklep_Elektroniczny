package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.ProduktParametr;

import java.util.List;

@Repository
public interface ProduktParametrRepository extends JpaRepository<ProduktParametr, Integer> {
    @Query(
            value = "SELECT * FROM produkt_parametr pp WHERE pp.id_produkt = ?1",
            nativeQuery = true)
    List<ProduktParametr> findParametryProduktu(int id_produkt);
}
