<script setup>
import { useLogStore } from "@/stores/logdata";
import { ref, watch } from "vue";

const logStore = useLogStore();
const downVal = ref(false);

watch(
  () => logStore.issue,
  () => {
    downVal.value = true;
    setTimeout(() => {
      downVal.value = false;
    }, 3000);
  }
);
</script>

<template>
  <div class="issue-con con box-md pre-t" :class="{ down: downVal }">
    새로운 부품을 생산하였습니다.
  </div>
</template>

<style scoped>
.issue-con {
  position: absolute;
  width: 350px;
  height: 50px;

  top: -70px;
  right: 150px;
  /* background-color: red; */

  transition: all 0.5s ease-in-out;
}

.down {
  top: 0;
}
</style>
