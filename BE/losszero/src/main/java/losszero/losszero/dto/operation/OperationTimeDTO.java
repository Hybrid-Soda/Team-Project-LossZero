package losszero.losszero.dto.operation;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationTimeDTO {
    private Long id;
    private Long lineId;
    private LocalDate operationDate;
    private LocalDateTime startTime;
    private Duration operationTime;
}




