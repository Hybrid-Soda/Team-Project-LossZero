<script setup>
import { useCounterStore } from "@/stores/counter";

defineProps({
  logData: Object,
});
</script>

<template>
  <div class="logtime-con con box-row pre-t">
    <div class="time">{{ logData.logDate }}</div>
    <div class="normal">
      <span>정상 : </span>
      <span>{{ logData.normal }} 개</span>
    </div>
    <div class="recycle">
      <span>재사용 가능 : </span>
      <span>{{ logData.reusable }} 개</span>
    </div>
    <div class="faulty">
      <span>불량 : </span>
      <span>{{ logData.defective }} 개</span>
    </div>
  </div>
</template>

<style scoped>
.logtime-con {
  width: 100%;
  height: 40px;
  margin: 10px 0px;
}

.time {
  margin-left: 15px;
  width: 110px;
}

.normal {
  margin-left: 15px;
  width: 80px;

  color: blue;
}

.normal,
.recycle,
.faulty {
  margin-left: 15px;
}

.recycle {
  width: 120px;

  color: green;
}

.faulty {
  width: 120px;

  color: red;
}
</style>
