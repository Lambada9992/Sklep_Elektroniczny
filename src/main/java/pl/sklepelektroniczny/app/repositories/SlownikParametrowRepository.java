package pl.sklepelektroniczny.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sklepelektroniczny.app.model.SlownikParametrow;

@Repository
public interface SlownikParametrowRepository extends JpaRepository<SlownikParametrow, Integer> {
}
