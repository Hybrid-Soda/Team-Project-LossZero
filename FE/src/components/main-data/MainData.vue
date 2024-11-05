<script setup>
import ProductionTarget from "@/components/main-data/ProductionTarget.vue";
import DPO from "@/components/main-data/DPO.vue";
import FPY from "@/components/main-data/FPY.vue";
import OEE from "@/components/main-data/OEE.vue";
import Clock from "@/components/main-data/Clock.vue";
import Sensor from "@/components/main-data/Sensor.vue";
import { onMounted } from "vue";
import { dailyProduct } from "@/api/data";
import { useCounterStore } from "@/stores/counter";

const cntStore = useCounterStore();

onMounted(() => {
  // dailyProduct();
  dailyProduct(currentDateCal())
    .then((res) => {
      cntStore.currentData(res.data);
      // console.log(res.data);
    })
    .catch((err) => {
      console.log(err);
    });
});

function currentDateCal() {
  const today = new Date();
  const lineId = 1;
  const startDate = formatDate(today);
  const endDate = formatDate(today);

  return { lineId, startDate, endDate };
}

function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}
</script>

<template>
  <div class="main-data-con">
    <ProductionTarget />
    <DPO />
    <FPY />
    <OEE />
    <Clock />
    <Sensor />
  </div>
</template>

<style scoped>
.main-data-con {
  width: 1500px;
  height: 80px;

  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
