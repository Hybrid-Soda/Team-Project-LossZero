<script setup>
import ProductionTarget from "@/components/main-data/ProductionTarget.vue";
import DPO from "@/components/main-data/DPO.vue";
import FPY from "@/components/main-data/FPY.vue";
import OEE from "@/components/main-data/OEE.vue";
import Date from "@/components/main-data/Date.vue";
import Sensor from "@/components/main-data/Sensor.vue";
</script>

<template>
  <div class="main-data-con">
    <ProductionTarget />
    <DPO />
    <FPY />
    <OEE />
    <Date />
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
