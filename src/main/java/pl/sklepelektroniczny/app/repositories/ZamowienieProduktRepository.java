package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.ZamowienieProdukt;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ZamowienieProduktRepository extends JpaRepository<ZamowienieProdukt, Integer> {

    @Query(value = "SELECT * FROM zamowienie_produkt zp WHERE zp.id_zamowienia = ?1", nativeQuery = true)
    List<ZamowienieProdukt> getZamowienieProduktByIdZamowienia(int id_zamowienia);

    @Query(value = "SELECT zp.* FROM zamowienie_produkt AS zp JOIN zamowienie AS z USING(id_zamowienia) " +
            "WHERE z.data_zamowienia BETWEEN ?1 AND ?2", nativeQuery = true)
    List<ZamowienieProdukt> getZamowieniaOkres(String od_daty, String do_daty);
}
