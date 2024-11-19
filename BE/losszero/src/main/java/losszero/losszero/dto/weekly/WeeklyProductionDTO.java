package losszero.losszero.dto.weekly;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyProductionDTO {
    private LocalDate date;
    private long sumNormal;
    private long sumDefective;
    private long sumReusable;
    private long total;
}
