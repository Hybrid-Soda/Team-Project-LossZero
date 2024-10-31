import { ref, computed } from "vue";
import { defineStore } from "pinia";

//mqtt 코드 추가
import createMQTTClient from "@/mqttClient";

/*
export const useEnvStore = defineStore("environment", () => {
  const humidity = ref(0);
  const temperature = ref(0);

  // MQTT 클라이언트 생성 및 메시지 수신 콜백 설정
  const mqttClient = createMQTTClient((message) => {
    const topic = message.destinationName;
    const data = JSON.parse(message.payloadString);

    if (topic === "sensor/temperature") {
      temperature.value = data.temperature;
    } else if (topic === "sensor/humidity") {
      humidity.value = data.humidity;
    }
  });

  // 연결 후 구독 설정
  mqttClient.connect({
    onSuccess: () => {
      mqttClient.subscribe("sensor/temperature");
      mqttClient.subscribe("sensor/humidity");
    },
    onFailure: () => {
      console.log("MQTT 연결 실패");
    }
  });

  return { humidity, temperature };
});

*/



export const useEnvStore = defineStore("environment", () => {
  const humidity = ref(0);
  const temperature = ref(0);

  function sseData(data) {
    humidity.value = data.humidity;
    temperature.value = data.temperature;
  }

  return { humidity, temperature, sseData };
});
