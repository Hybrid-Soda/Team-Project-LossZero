<script setup>
import ProductionTarget from "@/components/main-data/ProductionTarget.vue";
import DPO from "@/components/main-data/DPO.vue";
import FPY from "@/components/main-data/FPY.vue";
import OEE from "@/components/main-data/OEE.vue";
</script>

<template>
  <div class="main-data-con con shadow">
    <ProductionTarget />
    <DPO />
    <FPY />
    <OEE />
  </div>
</template>

<style scoped>
.main-data-con {
  width: 1500px;
  height: 120px;

  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  background-color: white;
}
</style>
