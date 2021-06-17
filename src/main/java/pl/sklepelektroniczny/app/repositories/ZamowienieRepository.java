package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Zamowienie;

import java.util.List;

@Repository
public interface ZamowienieRepository extends JpaRepository<Zamowienie, Integer> {
    @Modifying
    @Query(value = "UPDATE zamowienie z SET z.id_status = ?2 WHERE z.id_zamowienia = ?1", nativeQuery = true)
    void updateStatus(int id_zamowienia, int id_status);

    @Query(value = "SELECT * FROM zamowienie z WHERE z.id_uzytkownik = ?1", nativeQuery = true)
    List<Zamowienie> zamowieniaUzytkownika(int id_uzytkownik);
}
