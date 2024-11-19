package losszero.losszero.dto.realtime;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeCircumstanceDTO {
    private String sender;
    private int lineId;
    private CircumstanceDTO circumstance;
}
