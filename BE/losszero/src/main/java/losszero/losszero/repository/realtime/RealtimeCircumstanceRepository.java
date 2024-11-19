package losszero.losszero.repository.realtime;

import losszero.losszero.entity.realtime.RealtimeCircumstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealtimeCircumstanceRepository extends JpaRepository<RealtimeCircumstance, Long> {
    Optional<RealtimeCircumstance> findTop1ByLineIdOrderByCreatedAtDesc(int lineId);
}
