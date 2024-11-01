package losszero.losszero.mqtt;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MqttDto {
    private String sender; // 메시지 발신자 정보
    private String messasge; // 메시지 내용
}
