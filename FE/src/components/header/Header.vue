<script setup>
import { useRouter } from "vue-router";
import { logout } from "@/api/user";
import UserInfo from "@/components/header/UserInfo.vue";
import LogIssue from "@/components/header/LogIssue.vue";
import { useUserStore } from "@/stores/user";

// Vue Router 사용
const router = useRouter();
const userStore = useUserStore();

// 로그아웃 버튼 클릭 시 로그인 페이지로 이동
function logoutBtn() {
  logout()
    .then((res) => {
      userStore.logoutFun();
      router.push({ path: "/" });
    })
    .catch((err) => {
      console.log(err);
    });
}
</script>

<template>
  <div class="header-con">
    <LogIssue />
    <div class="left">
      <img src="@/assets/img/icon.svg" alt="아이콘" class="icon" />
      <span class="pre-t title"></span>
      <UserInfo />
    </div>

    <div class="right">
      <!-- 로그아웃 버튼 클릭 시 logout 함수 호출 -->
      <button class="logout pre-t btn" @click="logoutBtn">로그아웃</button>
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
  border-radius: 5px;
}
</style>
