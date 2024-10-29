package losszero.losszero.dto.realtime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class RealtimeProdDTO {
    private int normal;
    private int defective;
    private int reusable;
}