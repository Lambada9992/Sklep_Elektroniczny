package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Uzytkownik;

import java.util.Optional;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Integer> {
    Optional<Uzytkownik> findByLogin(String login);
}
