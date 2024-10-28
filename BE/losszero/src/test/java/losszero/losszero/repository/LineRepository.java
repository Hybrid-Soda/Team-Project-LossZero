package losszero.losszero.repository;

import losszero.losszero.entity.line.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Integer> {
}
