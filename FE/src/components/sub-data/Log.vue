<script setup>
import LogTime from "@/components/sub-data/LogTime.vue";
import { useCounterStore } from "@/stores/counter";
import { useLogStore } from "@/stores/logdata";
import { realtimeProd } from "@/api/data";
import { onMounted, ref, watch } from "vue";

const logStore = useLogStore();
const cntStore = useCounterStore();
const logData = ref([]);

let idx = 1;
const normalCnt = ref(0);
const recycleCnt = ref(0);
const faultyCnt = ref(0);

watch(
  () => cntStore.issue,
  () => {
    loadRealtimeProd();
  }
);

onMounted(() => {
  loadRealtimeProd();
});

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

function loadRealtimeProd() {
  realtimeProd()
    .then((res) => {
      console.log(res.data.data.products);
      logData.value = [];
      res.data.data.products.forEach((element) => {
        // console.log(element);

        const logDate = formatDate(element.createdAt);
        const normal = element.normal;
        const reusable = element.reusable;
        const defective = element.defective;

        logData.value.unshift({
          logDate,
          normal,
          reusable,
          defective,
        });
      });
    })
    .catch((err) => {
      console.log(err);
    });
}

function formatDate(date) {
  // console.log(date);
  const idx = date.indexOf(":");
  let hour = Number(date[idx - 2] + date[idx - 1]) + 9;
  const minute = date[idx + 1] + date[idx + 2] + "분";
  let ampm = "오전";

  if (hour > 12) {
    ampm = "오후";
    hour -= 12;
  }
  hour = hour.toString().padStart(2, "0") + "시";

  return `${ampm} ${hour} ${minute}`;
}
</script>

<template>
  <div style="width: 100%">
    <div class="log-con con shadow">
      <LogTime
        v-for="(logtime, index) in logData"
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
