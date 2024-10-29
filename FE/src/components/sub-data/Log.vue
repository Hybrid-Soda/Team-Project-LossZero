<script setup>
import LogTime from "@/components/sub-data/LogTime.vue";
import { useCounterStore } from "@/stores/counter";
import { useLogStore } from "@/stores/logdata";
import { ref, watch } from "vue";

const logStore = useLogStore();
const cntStore = useCounterStore();

let idx = 1;
const normalCnt = ref(0);
const recycleCnt = ref(0);
const faultyCnt = ref(0);

watch(
  () => cntStore.issue,
  () => {
    // console.log("이슈 생성!");
    logStore.createIssue();
    tempPlus();
  }
);

function tempPlus() {
  const logtime = {
    idx: idx,
    normal: cntStore.normalCnt,
    recycle: cntStore.recycleCnt,
    faulty: cntStore.faultyCnt,
  };

  cntStore.updateProductCnt(cntStore.sumNormal, logtime);

  idx += 1;
}
</script>

<template>
  <div style="width: 100%">
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
