<script setup>
import { useCounterStore } from "@/stores/counter";
import { onMounted, ref } from "vue";
import { setTargetProduction, lineState } from "@/api/data.js";

onMounted(() => {
  // 목표 생산량 받아오기
  lineState()
    .then((res) => {
      console.log(res.data);
    })
    .catch((err) => {
      console.log(err);
    });
});

const cntStore = useCounterStore();

const changeTarget = ref(false);
const targetCnt = ref(cntStore.targetCnt);

function changeBtn() {
  if (changeTarget.value) {
    // 목표 생산량 변경하기
    setTargetProduction({
      targetProduct: "120",
    })
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });

    // cntStore.changeTargetCnt(targetCnt.value);
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
        {{ cntStore.sumNormal }} /
        <input
          type="number"
          class="target pre-t"
          v-if="changeTarget"
          v-model="targetCnt"
          @keydown.enter="changeBtn"
        />
        <span v-else>{{ cntStore.targetCnt }}</span>
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
