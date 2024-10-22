<script setup>
defineProps({
  logData: Object,
});
</script>

<template>
  <div class="logtime-con con box-row pre-t">
    <span class="time">오전 10시 11분</span>
    <div class="nomal">
      <span>정상 : </span>
      <span>{{ logData.nomal }} 개</span>
    </div>
    <div class="recycle">
      <span>재사용 가능 : </span>
      <span>{{ logData.recycle }} 개</span>
    </div>
    <div class="faulty">
      <span>불량 : </span>
      <span>{{ logData.faulty }} 개</span>
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
}

.nomal {
  margin-left: 15px;
  width: 70px;

  color: blue;
}

.nomal,
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
