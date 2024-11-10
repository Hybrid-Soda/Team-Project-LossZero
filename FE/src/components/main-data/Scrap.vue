<script setup>
import { useCounterStore } from "@/stores/counter";
import { useLogStore } from "@/stores/logdata";
import { onMounted, ref, watch } from "vue";

const cntStore = useCounterStore();
const logStore = useLogStore();
const scrapVal = ref(0);

watch(
  () => logStore.issue,
  () => {
    // console.log("counting");
    countScrap();
  }
);

onMounted(() => {
  countScrap();
});

function countScrap() {
  console.log(cntStore.Scrap);
  scrapVal.value = 0;
  let counting = setInterval(() => {
    if (scrapVal.value >= cntStore.Scrap) {
      scrapVal.value = cntStore.Scrap;
      clearInterval(counting);
    } else {
      scrapVal.value += 1;
    }
  }, 10);
}
</script>

<template>
  <div class="content-con con shadow">
    <img src="@/assets/img/scrap.svg" alt="스크랩 비율" />

    <div class="title">
      <div class="label pre-t">
        <span>스크랩 비율</span>
      </div>
      <div class="value pre-t">
        <span>{{ scrapVal }}%</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content-con {
  width: 230px;
}
</style>
