package losszero.losszero.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_time")
public class OperationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_time_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate operationDate;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Long lineId;

    // Duration을 초로 저장
    @Column(name = "accumulated_time")
    private Long accumulatedTime = 0L;

    // Getter and Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    // 초로 저장된 accumulatedTime을 Duration으로 변환
    public Duration getAccumulatedTime() {
        return Duration.ofSeconds(accumulatedTime);
    }

    // Duration을 초 단위로 저장
    public void setAccumulatedTime(Duration accumulatedTime) {
        this.accumulatedTime = accumulatedTime.getSeconds();
    }
}
