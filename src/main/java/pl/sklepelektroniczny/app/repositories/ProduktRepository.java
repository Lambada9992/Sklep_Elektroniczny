package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Produkt;

import java.util.List;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Integer> {

    @Query(value = "SELECT * FROM produkt p WHERE p.id_typ = ?1 ", nativeQuery = true)
    public List<Produkt> getAllProduktyWhere(int id_typ);
}
