<script setup>
import { ref } from "vue";

const isOperate = ref(false);

function onOff() {
  isOperate.value = !isOperate.value;
}
</script>

<template>
  <div class="machine-con con shadow box-col pre-bt">
    <img src="@/assets/img/gif/camera.gif" alt="카메라" v-if="isOperate" />
    <img src="@/assets/img/camera.jpg" alt="카메라" v-else />
    <span class="is-operate"> {{ isOperate ? "작동중" : "정지" }} </span>
    <button @click="onOff">{{ isOperate ? "정지" : "작동" }}</button>
  </div>
</template>

<style scoped>
img {
  width: 150px;
  height: 150px;

  margin-bottom: 10px;
}

.is-operate {
  font-size: 20px;
}
</style>
