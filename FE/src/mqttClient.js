// src/mqttClient.js
import mqtt from 'paho-mqtt';

const createMQTTClient = (onMessageCallback) => {
  const client = new mqtt.Client("https://k11e202.p.ssafy.io/", 9001, "vue_client");

  client.onMessageArrived = onMessageCallback;

  const connect = () => {
    client.connect({
      onSuccess: () => {
        console.log("MQTT 연결 성공");
        subscribe("test");
      },
      onFailure: () => {
        console.log("MQTT 연결 실패");
      },
    });
  };

  const subscribe = (topic) => {
    client.subscribe(topic, {
      onSuccess: () => console.log(`구독 성공: ${topic}`),
      onFailure: () => console.log(`구독 실패: ${topic}`),
    });
  };

  const publish = (msg, topic = "test") => {
    const message = new mqtt.Message(msg);
    message.destinationName = topic;
    client.send(message);
  };

  return { connect, subscribe, publish };
};

export default createMQTTClient;
