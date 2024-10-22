<script setup>
// import { onMounted, ref } from "vue";

// onMounted(() => {
//   updateClock();
//   setInterval(updateClock, 1000);
// });
// function updateClock() {
//   const currentDate = new Date();
//   const year = currentDate.getFullYear();
//   const month = currentDate.getMonth() + 1;
//   let hours = currentDate.getHours();
//   const minutes = currentDate.getMinutes();
//   const date = currentDate.getDate();
//   const day = currentDate.getDay();

//   if (hours > 12) {
//     hours -= 12;
//   }
// }

const week = ["일", "월", "화", "수", "목", "금", "토"];
</script>

<template>
  <div class="date-con pre-t">
    <div>
      <span>{{ 2024 }}년 {{ 10 }}월 {{ 22 }}일 {{ "화" }}요일</span>
      <br />
      <div style="margin: 10px 20px">
        <span>{{ hours <= 12 ? "오전" : "오후" }} </span>
        <span class="time">{{ 11 }}:{{ 12 }}</span>
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
