<script setup>
import { ref } from "vue";

const isOperate = ref(false);

function onOff() {
  isOperate.value = !isOperate.value;
}
</script>

<template>
  <div class="machine-con con shadow box-col pre-bt">
    <!-- 작동중일 때는 gif, 그렇지 않을 때는 이미지 출력 -->
    <img src="@/assets/img/gif/belt.gif" alt="카메라" v-if="isOperate" />
    <img src="@/assets/img/belt.jpg" alt="카메라" v-else />
    
    <!-- 작동 상태 표시 -->
    <span class="is-operate"> {{ isOperate ? "벨트 동작중" : "벨트 정지" }} </span>
    
    <!-- 버튼 클릭 시 이미지와 상태 변경 -->
    <button @click="onOff" :class="{ 'inactive': !isOperate }">
      <img src="@/assets/img/Activating.svg" alt="Activating" :style="{ filter: isOperate ? 'none' : 'grayscale(100%)' }" />
    </button>
  </div>
</template>

<style scoped>
img {
  width: 150px;
  height: 150px;
}

.is-operate {
  font-size: 20px;
}

button {
  background-color: transparent;
  border: none;
  cursor: pointer;
}

button img {
  width: 50px;
  height: 50px;
}

/* 정지 상태일 때 버튼 아이콘의 색상을 변경 (회색) */
button.inactive img {
  color: #D9D9D9; /* 텍스트 색상을 바꾸고 싶을 경우 */
}

/* 일반 버튼 상태 */
button img {
  color: inherit;
}
</style>
