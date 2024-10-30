package losszero.losszero.handler;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.core.MqttPahoComponent;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;

// 연결이 끊겼는지 체크하다, 이벤트 발생시 확인하는 리스너
@Component
public class MqttEventHandler {

    @EventListener
    public void connectLost(MqttConnectionFailedEvent failedEvent){
        MqttPahoComponent source = failedEvent.getSourceAsType();
        MqttConnectOptions options = source.getConnectionInfo();
        System.out.println("MQTT Connection is broken!!");
    }
}
