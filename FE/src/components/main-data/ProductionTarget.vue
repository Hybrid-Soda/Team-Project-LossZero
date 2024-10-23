<script setup>
import { useCounterStore } from "@/stores/counter";
import { onMounted, ref } from "vue";

onMounted(() => {
  // 목표 생산량 받아오기
});

const cntStore = useCounterStore();

const changeTarget = ref(false);
const targetCnt = ref(120);

function changeBtn() {
  if (changeTarget.value) {
    // 목표 생산량 변경하기
  }

  changeTarget.value = !changeTarget.value;
}
</script>

<template>
  <div class="content-con con shadow">
    <img src="@/assets/img/production.svg" alt="목표 생산량" />

    <div class="title">
      <div class="box-row">
        <span class="label pre-t">목표 생산량</span>
        <button class="change btn pre-t" @click="changeBtn">변경</button>
      </div>
      <div class="value pre-t">
        {{ cntStore.productCnt }} /
        <input
          type="number"
          class="target pre-t"
          v-if="changeTarget"
          v-model="targetCnt"
          @keydown.enter="changeBtn"
        />
        <span v-else>{{ targetCnt }}</span>
        (개)
      </div>
    </div>
  </div>
</template>

<style scoped>
.label {
  margin-right: 35px;
}

.change {
  padding: 2px 5px;
  border-radius: 5px;
  font-size: 12px;
}

.target {
  width: 50px;
  height: 30px;
  font-size: 20px;
}
</style>
