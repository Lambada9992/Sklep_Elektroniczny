package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sklepelektroniczny.app.model.Uzytkownik;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Uzytkownik,Integer> {
    Optional<Uzytkownik> findByLogin(String login);
}
