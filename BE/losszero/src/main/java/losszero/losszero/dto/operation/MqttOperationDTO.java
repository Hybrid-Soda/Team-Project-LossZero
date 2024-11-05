package losszero.losszero.dto.operation;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqttOperationDTO {
    private String sender;
    private Long lineId;
    private String message;
}
