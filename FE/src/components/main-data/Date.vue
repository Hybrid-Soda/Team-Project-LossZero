<script setup>
import { onMounted, ref } from "vue";

const currentTime = ref({
  year: 0,
  month: 0,
  date: 0,
  day: 0,
  hours: 0,
  minutes: 0,
  period: "오전",
});

const week = ["일", "월", "화", "수", "목", "금", "토"];

function updateClock() {
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth() + 1;
  const date = currentDate.getDate();
  const day = currentDate.getDay();
  let hours = currentDate.getHours();
  const minutes = currentDate.getMinutes();
  
  const period = hours < 12 ? "오전" : "오후";
  if (hours > 12) hours -= 12; // 12시간제 처리

  // 시간 업데이트
  currentTime.value = {
    year,
    month,
    date,
    day,
    hours,
    minutes: minutes < 10 ? `0${minutes}` : minutes, // 2자리로 맞추기
    period,
  };
}

onMounted(() => {
  updateClock();
  setInterval(updateClock, 1000); // 1초마다 업데이트
});
</script>

<template>
  <div class="date-con pre-t">
    <div>
      <span>{{ currentTime.year }}년 {{ currentTime.month }}월 {{ currentTime.date }}일 {{ week[currentTime.day] }}요일</span>
      <br />
      <div style="margin: 10px 20px">
        <span>{{ currentTime.period }}</span>
        <span class="time">{{ currentTime.hours }}:{{ currentTime.minutes }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.date-con {
  width: 200px;
  height: 100%;

  margin-top: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.time {
  font-size: 35px;
  margin-left: 15px;
}
</style>
