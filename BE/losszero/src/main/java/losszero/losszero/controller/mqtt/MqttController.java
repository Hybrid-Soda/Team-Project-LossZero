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
import org.springframework.stereotype.Controller;
import org.springframework.messaging.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MqttController {
    private final OperationTimeService operationTimeService;
    private final RealtimeProductService realtimeProductService;
    private final RealtimeCircumstanceService realtimeCircumstanceService;
    private final IMqttClient mqttClient;
    private final Gson gson = new Gson();

    @Bean
    @ServiceActivator(inputChannel = "realtimeInputChannel")
    public MessageHandler inboundMessageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            String json = (String) message.getPayload();

            switch (Objects.requireNonNull(topic)) {
                case "realtime-oper":
                    handleOperationMessage(json);
                    break;
                case "realtime-prod":
                    handleProductMessage(json);
                    break;
                case "realtime-circ":
                    handleCircumstanceMessage(json);
                    break;
                default:
                    System.out.println("Unknown topic");
            }
        };
    }

    private void handleOperationMessage(String json) {
        MqttOperationDTO mqttOperationDTO = gson.fromJson(json, MqttOperationDTO.class);
        String oper = mqttOperationDTO.getMessage();
        Long lineId = mqttOperationDTO.getLineId();

        if ("on".equals(oper)) {
            operationTimeService.startOperation(lineId);
            publish("realtime-oper", "message", "공장 가동 시작하였습니다.");
        } else if ("off".equals(oper)) {
            operationTimeService.endOperation(lineId);
            publish("realtime-oper", "message", "공장 가동 정지하였습니다.");
        }
    }

    private void handleProductMessage(String json) {
        try {
            RealtimeProductDTO productDTO = gson.fromJson(json, RealtimeProductDTO.class);
            realtimeProductService.saveProductData(productDTO);
        } catch (RuntimeException e) {
            publish("realtime-prod", "error", "Invalid MQTT message format.");
        }
    }

    private void handleCircumstanceMessage(String json) {
        try {
            RealtimeCircumstanceDTO circumstanceDTO = gson.fromJson(json, RealtimeCircumstanceDTO.class);
            realtimeCircumstanceService.saveCircumstanceData(circumstanceDTO);
        } catch (RuntimeException e) {
            publish("realtime-circ", "error", "Invalid MQTT message format.");
        }
    }

    public void publish(String pubTopic, String property, String value) {
        JsonObject object = new JsonObject();
        object.addProperty(property, value);

        MqttMessage mqttMessage = new MqttMessage(object.toString().getBytes(StandardCharsets.UTF_8));
        mqttMessage.toString();
        mqttMessage.setQos(2);
        mqttMessage.setRetained(false);

        try {
            if (!mqttClient.isConnected()) {
                mqttClient.connect();
            }
            mqttClient.publish(pubTopic, mqttMessage);
        } catch (MqttException e) {
            System.err.println("Failed to publish message: " + e.getMessage());
        }
    }
}
