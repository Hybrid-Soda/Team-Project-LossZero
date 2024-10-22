<script setup>
import UserInfo from "@/components/header/UserInfo.vue";
import LogIssue from "@/components/header/LogIssue.vue";
</script>

<template>
  <div class="header-con">
    <LogIssue />
    <div class="left">
      <img src="@/assets/img/icon.svg" alt="아이콘" class="icon" />
      <span class="pre-t title">불량 제로</span>
      <UserInfo />
    </div>

    <div class="right">
      <button class="logout pre-t btn">로그아웃</button>
    </div>
  </div>
</template>

<style scoped>
.header-con {
  width: 1500px;
  height: 50px;
  margin-top: 10px;

  display: flex;
  justify-content: space-between;
  align-items: center;

  position: relative;
}

.icon {
  scale: 0.8;
}
.pre-t {
  font-size: 25px;
}

.left,
.right {
  display: flex;
  align-items: center;
}

.logout {
  width: 80px;
  height: 40px;
  font-size: 15px;
  border-radius: 10px;
}
</style>
