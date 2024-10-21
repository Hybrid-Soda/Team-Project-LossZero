<script setup>
import UserInfo from "@/components/header/UserInfo.vue";
</script>

<template>
  <div class="header-con">
    <img src="@/assets/img/icon.svg" alt="아이콘" class="icon" />
    <span class="pre-t title">Smarter</span>
    <UserInfo />
  </div>
</template>

<style scoped>
.header-con {
  width: 1500px;
  height: 50px;
  margin-top: 10px;

  display: flex;
  align-items: center;
}

.icon {
  scale: 0.8;
}
.pre-t {
  font-size: 25px;
}
</style>
