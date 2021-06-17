package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.ZamowienieProdukt;

import java.util.List;

@Repository
public interface ZamowienieProduktRepository extends JpaRepository<ZamowienieProdukt, Integer> {

    @Query(value = "SELECT * FROM zamowienie_produkt zp WHERE zp.id_zamowienia = ?1", nativeQuery = true)
    List<ZamowienieProdukt> getZamowienieProduktByIdZamowienia(int id_zamowienia);
}
