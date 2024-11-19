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
  <div class="header-wrapper">
    <div class="header-con">
      <div class="left">
        <!-- 로고와 유저 정보를 함께 배치 -->
        <img src="@/assets/img/icon9.svg" alt="아이콘" class="icon" />
        <div class="user-info">
          <UserInfo />
        </div>
      </div>
      <LogIssue />
      <div class="right">
        <button class="logout pre-t btn" @click="logoutBtn">로그아웃</button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.header-wrapper {
  width: 100%;
  background-color: #4890ef;
}

.header-con {
  max-width: 1500px;
  height: 50px;
  margin: 0 auto;
  display: flex;
  align-items: center; /* 세로 정렬 */
  justify-content: space-between; /* 양쪽 정렬 */
}

.left {
  display: flex;
  align-items: center; /* 세로 정렬 */
  justify-content: flex-start; /* 가로 정렬 */
  flex: 1; /* 왼쪽 컨테이너가 최대한 왼쪽으로 확장 */
}

.right {
  display: flex;
  align-items: center;
}

.icon {
  position: absolute; /* 위치를 절대값으로 설정 */
  top: -40px;
  right: 1200px; /* 화면 왼쪽으로부터 10px만큼 이동 */
  scale: 0.30;
  margin-right: 10px; /* 로고와 유저 정보 사이 간격 */
}


.user-info {
  position: absolute;
  left: 250px; 
  font-size: 18px;
  color: #fff;
}

.logout {
  width: 80px;
  height: 40px;
  font-size: 15px;
  border-radius: 5px;
}

</style>