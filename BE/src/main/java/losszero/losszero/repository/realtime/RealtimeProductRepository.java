package losszero.losszero.repository.realtime;

import losszero.losszero.entity.realtime.RealtimeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface RealtimeProductRepository extends JpaRepository<RealtimeProduct, Long> {
    Collection<RealtimeProduct> findByLineIdAndCreatedAtBetween(int lineId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
