package losszero.losszero.entity.operation;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operation_time")
public class OperationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_time_id")
    private Long id;

    @Column(nullable = false)
    private Long lineId;

    @Column(nullable = false)
    private LocalDate operationDate;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Long accumulatedTime = 0L;

    public Duration getAccumulatedTime() {
        return Duration.ofSeconds(accumulatedTime);
    }

    public void setAccumulatedTime(Duration accumulatedTime) {
        this.accumulatedTime = accumulatedTime.getSeconds();
    }
}
