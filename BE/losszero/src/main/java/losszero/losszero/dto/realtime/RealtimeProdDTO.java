package losszero.losszero.dto.realtime;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeProdDTO {
    private int normal;
    private int defective;
    private int reusable;
}