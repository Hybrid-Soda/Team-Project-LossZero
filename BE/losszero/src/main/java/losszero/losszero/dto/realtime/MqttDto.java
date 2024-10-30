package losszero.losszero.dto.realtime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MqttDto {
    private String sender; // 메시지 발신자 정보
    private String messasge; // 메시지 내용
}
