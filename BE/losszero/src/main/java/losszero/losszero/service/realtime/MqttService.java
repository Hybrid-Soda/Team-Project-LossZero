package losszero.losszero.service.realtime;

import losszero.losszero.dto.realtime.MqttDto;

public interface MqttService {
    void publish(MqttDto dto); // MQTT 메시지 발행
    boolean connected(); // MQTT 클라이언트의 연결 상태 확인
    void reconnect(); // MQTT 브로커와의 재연결 시도
}
