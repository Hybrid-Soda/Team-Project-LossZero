package losszero.losszero.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OperationTimeDTO {

    private Long lineId;
    private Long cycleProdId;
    private LocalDate operationDate;
    private LocalDateTime startTime;
    private Duration operationTime;

    // 기본 생성자
    public OperationTimeDTO() {}

    // 모든 필드를 받는 생성자
    public OperationTimeDTO(Long lineId, Long cycleProdId, LocalDate operationDate, LocalDateTime startTime, Duration operationTime) {
        this.lineId = lineId;
        this.cycleProdId = cycleProdId;
        this.operationDate = operationDate;
        this.startTime = startTime;
        this.operationTime = operationTime;
    }

    // Getter and Setter

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getCycleProdId() {
        return cycleProdId;
    }

    public void setCycleProdId(Long cycleProdId) {
        this.cycleProdId = cycleProdId;
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

    public Duration getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Duration operationTime) {
        this.operationTime = operationTime;
    }
}




