package losszero.losszero.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;

public class MqttOperationService implements MqttService{

    @Autowired
    private IMqttClient mqttClient;

    @Override
    public void publish(MqttDto dto) {

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
