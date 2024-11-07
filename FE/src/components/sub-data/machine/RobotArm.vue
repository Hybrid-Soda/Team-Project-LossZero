<script setup>
import { useOperateStore } from "@/stores/operate";
import { ref } from "vue";

const operateStore = useOperateStore();
</script>

<template>
  <div class="machine-con con shadow box-col pre-bt">
    <!-- 작동중일 때는 gif, 그렇지 않을 때는 이미지 출력 -->
    <img
      src="@/assets/img/gif/robot-arm.gif"
      alt="로봇 팔"
      v-if="operateStore.armOnOff"
    />
    <img src="@/assets/img/robot-arm.jpg" alt="로봇 팔" v-else />

    <!-- 작동 상태 표시 -->
    <span class="is-operate">
      {{ operateStore.armOnOff ? "로봇팔 동작중" : "로봇팔 정지" }}
    </span>
    <div v-if="operateStore.armOnOff">
      <img
        src="@/assets/img/loading-3.svg"
        alt="톱니"
        class="cogwheel-1 loading-1"
      />
      <img
        src="@/assets/img/loading-3.svg"
        alt="톱니"
        class="cogwheel-2 loading-2"
      />
    </div>
    <div v-else>
      <img src="@/assets/img/unloading.svg" alt="톱니" class="cogwheel-1" />
      <img src="@/assets/img/unloading.svg" alt="톱니" class="cogwheel-2" />
    </div>
  </div>
</template>

<style scoped>
img {
  width: 125px;
  height: 125px;
}

.is-operate {
  font-size: 20px;
  position: absolute;
  top: 130px;
}

.cogwheel-1,
.cogwheel-2 {
  position: absolute;
}

.cogwheel-1 {
  width: 40px;
  bottom: -32px;
  left: 85px;
}

.cogwheel-2 {
  width: 32px;
  bottom: -14px;
  left: 121px;

  transform: rotate(-30deg);
}

.loading-1 {
  animation: rotate_image 10s linear infinite;
  transform-origin: 50% 50%;
}

.loading-2 {
  animation: rotate_image_reverse 10s linear infinite;
  transform-origin: 50% 50%;
}

@keyframes rotate_image {
  100% {
    transform: rotate(360deg);
  }
}
@keyframes rotate_image_reverse {
  100% {
    transform: rotate(-390deg);
  }
}
</style>
