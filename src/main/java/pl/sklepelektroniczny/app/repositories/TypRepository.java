package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.Typ;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypRepository extends JpaRepository<Typ, Integer> {
    public Optional<Typ> findByNazwa(String nazwa);

    public List<Typ> findByRodzicIsNull();

    public List<Typ> findByRodzic(Typ rodzic);
}
