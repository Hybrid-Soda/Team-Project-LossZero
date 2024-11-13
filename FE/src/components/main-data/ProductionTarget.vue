<script setup>
import { useCounterStore } from "@/stores/counter";
import { onMounted, ref, watch } from "vue";
import { setTargetProduction, lineState } from "@/api/data.js";
import { useLogStore } from "@/stores/logdata";

const cntStore = useCounterStore();
const logStore = useLogStore();
const changeTarget = ref(false);
const targetCnt = ref(cntStore.targetCnt);

defineProps({
  sumNormal: Number,
});
</script>

<template>
  <div class="content-con con shadow">
    <img src="@/assets/img/production2.svg" alt="목표 생산량" />

    <div class="title">
      <div class="box-row">
        <span class="label pre-t">목표 생산량</span>
      </div>
      <div class="value pre-t">
        {{ sumNormal }} /
        <span>{{ cntStore.targetCnt }}</span>
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
