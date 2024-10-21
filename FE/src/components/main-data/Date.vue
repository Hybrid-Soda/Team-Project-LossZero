<script setup></script>

<template>
  <div class="date-con pre-t">
    <div>
      <span>2024년 10월 21일 월요일</span>
      <br />
      <div style="margin: 10px 15px">
        <span>오전 </span> <span class="time">10:21</span>
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
  margin-left: 10px;
}
</style>
