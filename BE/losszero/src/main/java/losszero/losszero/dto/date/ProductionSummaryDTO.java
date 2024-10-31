package losszero.losszero.dto.date;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductionSummaryDTO {
    private long sumNormal;
    private long sumDefective;
    private long sumReusable;
    private long total;
}
