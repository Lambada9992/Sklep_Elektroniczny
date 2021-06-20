package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Koszyk;

import java.util.List;

@Repository
public interface KoszykRepository extends JpaRepository<Koszyk, Integer> {

    @Query(value = "SELECT * FROM koszyk k WHERE k.id_uzytkownik = ?1 AND k.id_produkt = ?2", nativeQuery = true)
    Koszyk findById_uzytkownikAndId_produkt(int id_uzytkownik, int id_produkt);

    @Query(value = "SELECT * FROM koszyk k WHERE k.id_uzytkownik = ?1", nativeQuery = true)
    List<Koszyk> findKoszykUzytkownika(int id_uzytkownika);

    @Query(value = "SELECT COUNT(*) FROM koszyk k WHERE k.id_uzytkownik = ?1", nativeQuery = true)
    int findIloscPozycjiUzytkownika(int id_uzytkownika);

    @Modifying
    @Query(value = "UPDATE koszyk k SET k.ilosc = ?2 WHERE k.id_koszyk = ?1", nativeQuery = true)
    void updateIlosc(int id_koszyk, int ilosc);

    @Modifying
    @Query(value = "DELETE FROM koszyk k WHERE k.id_koszyk = ?1", nativeQuery = true)
    void deleteById_koszyk(int id_koszyk);

    @Modifying
    @Query(value = "DELETE FROM koszyk k WHERE k.id_uzytkownik = ?1", nativeQuery = true)
    void deleteCalyKoszykUzytkownika(int id_uzytkownik);




}
