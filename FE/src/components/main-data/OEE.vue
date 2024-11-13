<script setup>
import { useCounterStore } from "@/stores/counter";
import { useLogStore } from "@/stores/logdata";
import { onMounted, ref, watch } from "vue";

const cntStore = useCounterStore();
const logStore = useLogStore();
const oeeVal = ref(0);

watch(
  () => logStore.issue,
  () => {
    // console.log("counting");
    countOee();
  }
);

onMounted(() => {
  countOee();
});

function countOee() {
  console.log(cntStore.OEE);
  oeeVal.value = 0;
  let counting = setInterval(() => {
    if (oeeVal.value >= cntStore.OEE) {
      oeeVal.value = cntStore.OEE;
      clearInterval(counting);
    } else {
      oeeVal.value += 1;
    }

    // console.log(dpoVal.value);
  }, 10);
}
</script>

<template>
  <div class="content-con con shadow">
    <img src="@/assets/img/OEE2.svg" alt="설비 종합 효율" />

    <div class="title">
      <div class="label pre-t">
        <span>설비 종합 효율</span>
        <span class="spelling">OEE</span>
      </div>

      <div class="value pre-t">
        <span> {{ oeeVal }}% </span>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
