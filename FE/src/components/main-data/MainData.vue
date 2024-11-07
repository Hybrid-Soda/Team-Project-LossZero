<script setup>
import ProductionTarget from "@/components/main-data/ProductionTarget.vue";
import DPO from "@/components/main-data/DPO.vue";
import FPY from "@/components/main-data/FPY.vue";
import OEE from "@/components/main-data/OEE.vue";
import Clock from "@/components/main-data/Clock.vue";
import Sensor from "@/components/main-data/Sensor.vue";
import { onMounted, ref, watch } from "vue";
import { dailyProduct } from "@/api/data";
import { useCounterStore } from "@/stores/counter";
import { useLogStore } from "@/stores/logdata";

const cntStore = useCounterStore();
const logStore = useLogStore();

const sumNormal = ref(0);
const sumDefective = ref(0);
const sumReusable = ref(0);
const total = ref(0);

onMounted(() => {
  // dailyProduct(currentDateCal())
  //   .then((res) => {
  //     cntStore.currentData(res.data);
  //     // console.log(res.data);
  //   })
  //   .catch((err) => {
  //     console.log(err);
  //   });
  loadDailyProd();
});

watch(
  () => logStore.issue,
  () => {
    loadDailyProd();
  }
);

function loadDailyProd() {
  dailyProduct(currentDateCal())
    .then((res) => {
      console.log(res.data);
      sumNormal.value = res.data.sumNormal;
      sumDefective.value = res.data.sumDefective;
      sumReusable.value = res.data.sumReusable;
      total.value = res.data.total;
      cntStore.currentData(res.data);
    })
    .catch((err) => {
      console.log(err);
    });
}

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
    <ProductionTarget :sum-normal="sumNormal" />
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
