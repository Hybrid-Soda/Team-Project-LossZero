<script setup>
import RealTime from "@/components/sub-data/RealTime.vue";
import WeeklyRecord from "@/components/sub-data/WeeklyRecord.vue";
import ProductionRecord from "@/components/sub-data/ProductionRecord.vue";
</script>

<template>
  <div class="sub-data-con">
    <RealTime />
    <div class="record-con">
      <ProductionRecord />
      <WeeklyRecord />
    </div>
  </div>
</template>

<style scoped>
.sub-data-con {
  width: 1500px;
  height: 540px;

  margin-top: 20px;
  display: flex;
  /* background-color: white; */
}

.record-con {
  width: 680px;
  margin-left: 20px;
  height: 100%;
  /* background-color: blue; */
}
</style>
