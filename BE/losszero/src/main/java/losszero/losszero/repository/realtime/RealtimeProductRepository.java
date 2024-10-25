package losszero.losszero.repository.realtime;

import losszero.losszero.entity.realtime.RealtimeProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealtimeProductRepository extends JpaRepository<RealtimeProd, Long> {
    Optional<RealtimeProd> findTop1ByLineIdOrderByCreatedAtDesc(int lineId);  // Optional로 변경
}
