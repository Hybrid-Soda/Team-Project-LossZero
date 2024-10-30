package losszero.losszero.dto.realtime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RealtimeCircumstanceDTO {
    private float temperature;
    private float humidity;
}
