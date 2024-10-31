package losszero.losszero.service.realtime;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import losszero.losszero.dto.realtime.MqttDto;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Service
public class MqttServiceImpl implements MqttService {

    private final String pubTopic = "test";

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Override
    public void publish(MqttDto dto) {
        Gson gson = new Gson();
        String dtoPayload = gson.toJson(dto);
        JsonObject object = new JsonObject();
        object.addProperty("Payload", dtoPayload);

        String payload = gson.toJson(object);

        int qos = 0;
        boolean retained = false;

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);

        try {
            mqttClient.publish(pubTopic, mqttMessage);
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean connected() {
        return mqttClient.isConnected();
    }

    @Override
    public void reconnect() {
        try {
            System.out.println("Trying to reconnect to MQTT server");
            mqttClient.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
