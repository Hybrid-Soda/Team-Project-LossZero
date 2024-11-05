package losszero.losszero.controller.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.operation.MqttOperationDTO;
import losszero.losszero.dto.realtime.RealtimeCircumstanceDTO;
import losszero.losszero.dto.realtime.RealtimeProductDTO;
import losszero.losszero.service.operation.OperationTimeService;
import losszero.losszero.service.realtime.RealtimeCircumstanceService;
import losszero.losszero.service.realtime.RealtimeProductService;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.MqttPahoComponent;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MqttController {
    private final OperationTimeService operationTimeService;
    private final RealtimeProductService realtimeProductService;
    private final RealtimeCircumstanceService realtimeCircumstanceService;
    private final IMqttClient mqttClient;

    @Bean
    @ServiceActivator(inputChannel = "realtimeInputChannel")
    public MessageHandler inboundMessageHandler() {
        Gson gson = new Gson();

        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            String json = (String) message.getPayload();

            switch (Objects.requireNonNull(topic)) {
                case "realtime-oper":
                    MqttOperationDTO mqttOperationDTO = gson.fromJson(json, MqttOperationDTO.class);
                    String oper = mqttOperationDTO.getMessage();
                    Long lineId = mqttOperationDTO.getLineId();

                    if (Objects.equals(oper, "on")) {
                        operationTimeService.startOperation(lineId);
//                        publish("realtime-oper", "message", "공장 가동 시작하였습니다.");
                    } else if (Objects.equals(oper, "off")) {
                        operationTimeService.endOperation(lineId);
//                        publish("realtime-oper", "message", "공장 가동 정지하였습니다.");
                    }
                    break;
                case "realtime-prod":
                    try {
                        RealtimeProductDTO productDTO = gson.fromJson(json, RealtimeProductDTO.class);
                        realtimeProductService.saveProductData(productDTO);
                    } catch (RuntimeException e) {
                        publish("realtime-prod", "error", "MQTT 메세지 형식이 잘못되었습니다.");
                    }
                    break;
                case "realtime-circ":
                    try {
                        RealtimeCircumstanceDTO circumstanceDTO = gson.fromJson(json, RealtimeCircumstanceDTO.class);
                        realtimeCircumstanceService.saveCircumstanceData(circumstanceDTO);
                    } catch (RuntimeException e) {
                        publish("realtime-circ", "error", "MQTT 메세지 형식이 잘못되었습니다.");
                    }
                    break;
                default:
                    System.out.println("Unknown topic");
            }
        };
    }

    public void publish(String pubTopic, String property, String value) {
        Gson gson = new Gson();

        JsonObject object = new JsonObject();
        object.addProperty(property, value);

        String payload = gson.toJson(object);

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(1);
        mqttMessage.setRetained(false);

        try {
            mqttClient.connect();
            mqttClient.publish(pubTopic, mqttMessage);
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @EventListener
    public void connectLost(MqttConnectionFailedEvent failedEvent){
        MqttPahoComponent source = failedEvent.getSourceAsType();
        MqttConnectOptions options = source.getConnectionInfo();
        System.out.println("MQTT Connection is broken!!");
    }
}
