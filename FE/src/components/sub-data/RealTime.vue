<script setup>
import Log from "@/components/sub-data/Log.vue";
</script>

<template>
  <div class="real-time-con con shadow">
    <div class="box-row title-con">
      <img src="@/assets/img/log.svg" alt="실시간 " />
      <span class="title pre-bt">실시간 정보</span>
    </div>
    <Log />
  </div>
</template>

<style scoped>
.real-time-con {
  width: 800px;
  height: 100%;

  padding: 10px 20px;

  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-color: red; */
}
.title-con {
  width: 100%;
}
.title {
  font-size: 25px;
  font-weight: 900;
}
</style>
