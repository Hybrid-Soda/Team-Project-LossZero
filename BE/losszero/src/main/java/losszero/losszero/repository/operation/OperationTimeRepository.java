package losszero.losszero.repository.operation;

import losszero.losszero.entity.operation.OperationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OperationTimeRepository extends JpaRepository<OperationTime, Long> {
    Optional<OperationTime> findByLineIdAndOperationDate(Long lineId, LocalDate operationDate);

    Optional<OperationTime> findTopByLineIdOrderByIdDesc(Long lineId);
}
