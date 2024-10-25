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
    <div style="position: relative; width: 100%; height: 100%">
      <img src="@/assets/img/issue.svg" alt="바퀴" class="issue-icon" />
      <span class="title pre-t">부품 생산</span>
      <span class="content pre-bt">새로운 부품이 생산되었습니다.</span>
    </div>
  </div>
</template>

<style scoped>
.issue-con {
  position: absolute;
  width: 350px;
  height: 60px;

  top: -70px;
  right: 150px;
  /* background-color: red; */

  transition: all 0.5s ease-in-out;
}

.issue-icon {
  width: 20px;

  position: absolute;
  top: 8px;
  left: 10px;
}
.title {
  position: absolute;
  width: 45px;
  top: 8px;
  left: 15px;
  font-size: 12px;
}

.content {
  position: absolute;
  top: 28px;
  left: 34px;
  font-size: 20px;
}
.down {
  top: -5px;
}
</style>
