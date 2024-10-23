<script setup>
import LogTime from "@/components/sub-data/LogTime.vue";
import { useCounterStore } from "@/stores/counter";
import { useLogStore } from "@/stores/logdata";
import { ref } from "vue";

const logStore = useLogStore();
const cntStore = useCounterStore();

let idx = 1;
const nomalCnt = ref(0);
const recycleCnt = ref(0);
const faultyCnt = ref(0);

function tempPlus() {
  const logtime = {
    idx: idx,
    nomal: nomalCnt.value,
    recycle: recycleCnt.value,
    faulty: faultyCnt.value,
  };

  cntStore.updateProductCnt(nomalCnt.value, logtime);
  logStore.createIssue();

  idx += 1;
  nomalCnt.value = 0;
  recycleCnt.value = 0;
  faultyCnt.value = 0;
}
</script>

<template>
  <div style="width: 100%">
    <div style="position: absolute; top: 20px; right: 520px">
      <label for="">정상</label>
      <input type="number" name="" id="" v-model="nomalCnt" />
      <label for="">재사용</label>
      <input type="number" name="" id="" v-model="recycleCnt" />
      <label for="">불량</label>
      <input type="number" name="" id="" v-model="faultyCnt" />

      <button style="margin-left: 10px" @click="tempPlus">전송</button>
    </div>
    <div class="log-con con shadow">
      <LogTime
        v-for="(logtime, index) in cntStore.logData"
        :key="index"
        :log-data="logtime"
      />
    </div>
  </div>
</template>

<style scoped>
.log-con {
  width: 100%;
  height: 160px;
  margin-top: 20px;
  padding: 0px 20px;
  overflow-y: scroll;
  background-color: #e1e8f8;

  transition: all 1s ease-in;
}

input {
  width: 40px;
}
label {
  margin-left: 10px;
}
</style>
