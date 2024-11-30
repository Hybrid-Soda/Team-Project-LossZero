package losszero.losszero.dto.date;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CircumstanceSummaryDTO {
    private float maxTemp;
    private float minTemp;
    private float maxHumid;
    private float minHumid;
}
