<script setup>
import Header from "@/components/header/Header.vue";
import MainData from "@/components/main-data/MainData.vue";
import SubData from "@/components/sub-data/SubData.vue";
import { useUserStore } from "@/stores/user";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

onMounted(() => {
  if (!userStore.isLogin) {
    router.replace({ name: "login" });
  }
});
</script>

<template>
  <div class="main-con">
    <Header />
    <MainData />
    <SubData />
  </div>
</template>

<style scoped>
.main-con {
  width: 100vw;
  height: 100vh;

  background-color: #e1e8f8;

  display: flex;
  flex-direction: column;
  align-items: center;

  overflow: hidden;
}
</style>
