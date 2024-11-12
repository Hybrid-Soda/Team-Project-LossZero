import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useEnvStore = defineStore(
  "environment",
  () => {
    const humidity = ref(0);
    const temperature = ref(0);

    function mqttData(data) {
      humidity.value = data.humidity;
      temperature.value = data.temperature;
    }

    return { humidity, temperature, mqttData };
  },
  { persist: true }
);
