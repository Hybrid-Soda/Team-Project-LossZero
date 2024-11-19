package losszero.losszero.dto.realtime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QualityDTO {
    private int normal;
    private int defective;
    private int reusable;
}
