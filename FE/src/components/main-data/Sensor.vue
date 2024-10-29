<script setup>
import { useEnvStore } from "@/stores/environment";

const envStore = useEnvStore();
</script>

<template>
  <div class="sensor-con pre-t">
    <div class="temperature box-row">
      <span>{{ envStore.temperature }}°C</span>
      <div class="box-row">
        <img src="@/assets/img/temperature.svg" alt="온도" />
        <span> 온도 </span>
      </div>
    </div>
    <div class="humidity box-row">
      <span>{{ envStore.humidity }}%</span>
      <div class="box-row">
        <img src="@/assets/img/humidity.svg" alt="습도" />
        <span> 습도 </span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.sensor-con {
  width: 150px;
  height: 100%;

  margin-right: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* align-items: center; */
}

img {
  margin: 5px 10px;
}

.humidity,
.temperature {
  justify-content: space-between;
}
</style>
