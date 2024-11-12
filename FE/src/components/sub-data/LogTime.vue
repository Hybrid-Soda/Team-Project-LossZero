<script setup>
import { useCounterStore } from "@/stores/counter";
import { ref, onMounted } from 'vue';

defineProps({
  logData: Object,
});

const currentDate = ref({
  year: 0,
  month: 0,
  date: 0,
  day: 0,
});

const week = ["일", "월", "화", "수", "목", "금", "토"];

function updateDate() {
  const now = new Date();
  currentDate.value = {
    year: now.getFullYear(),
    month: now.getMonth() + 1,
    date: now.getDate(),
    day: now.getDay(),
  };
}

onMounted(() => {
  updateDate();
});
</script>

<template>
  <div class="logtime-con con box-row pre-t">
    <div class="time">
      <span class="date">
        {{ currentDate.month }}월 {{ currentDate.date }}일 
        {{ week[currentDate.day] }}요일
      </span>
      {{ logData.logDate }}
    </div>
    <div class="separator">|</div>
    <div class="normal">
      <span>정상 : </span>
      <span>{{ logData.normal }} 개</span>
    </div>
    <!-- <div class="separator">|</div> -->
    <div class="recycle">
      <span>재사용 가능 : </span>
      <span>{{ logData.reusable }} 개</span>
    </div>
    <!-- <div class="separator">|</div> -->
    <div class="faulty">
      <span>불량 : </span>
      <span>{{ logData.defective }} 개</span>
    </div>
  </div>
</template>

<style scoped>
.logtime-con {
  display: flex;
  align-items: center;
  width: 100%;
  height: 40px;
  margin: 10px 0;
  padding: 0 15px;
}

.time {
  /* margin-left: 15px; */
  width: 230px;  /* 너비를 늘려 날짜와 시간을 수용합니다 */
}

.date {
  margin-right: 10px;
}

.separator {
  color: #ccc;
  font-weight: bold;
}

.normal {
  margin-left: 15px;
  width: 80px;
  color: #1976D2;
}

.normal,
.recycle,
.faulty {
  margin-left: 15px;
  width: 80px;
}

.recycle {
  width: 120px;
  color: #FF9F40;
}

.faulty {
  width: 120px;
  color: red;
}
</style>