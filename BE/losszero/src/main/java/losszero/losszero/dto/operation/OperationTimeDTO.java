package losszero.losszero.dto.operation;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OperationTimeDTO {
    private Long lineId;
    private Long cycleProdId;
    private LocalDate operationDate;
    private LocalDateTime startTime;
    private Duration operationTime;
}
