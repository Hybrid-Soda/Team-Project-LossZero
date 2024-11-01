package losszero.losszero.dto.realtime;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeProductDTO {
    private String sender;
    private int lineId;
    private QualityDTO quality;
}
