<script setup>
import { useCounterStore } from "@/stores/counter";
import { onMounted, ref, watch } from "vue";

const cntStore = useCounterStore();
const dpoVal = ref(0);

watch(
  () => cntStore.DPO,
  () => {
    // console.log("counting");
    countDPO();
  }
);

onMounted(() => {
  countDPO();
});

function countDPO() {
  // console.log(cntStore.DPO);
  dpoVal.value = 0;
  let counting = setInterval(() => {
    if (dpoVal.value >= cntStore.DPO) {
      dpoVal.value = cntStore.DPO;
      clearInterval(counting);
    } else {
      dpoVal.value += 1;
    }

    // console.log(dpoVal.value);
  }, 10);
}
</script>

<template>
  <div class="content-con con shadow">
    <img src="@/assets/img/DPO.svg" alt="공정 불량률" />

    <div class="title">
      <div class="label pre-t">
        <span>공정 불량률</span>
        <span class="spelling">DPO</span>
      </div>
      <div class="value pre-t">
        <span>{{ dpoVal }}%</span>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
