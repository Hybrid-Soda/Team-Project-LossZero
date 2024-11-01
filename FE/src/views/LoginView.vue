<template>
  <div class="loginContainer">
    <div class="form-wrapper">
      <div>
        <img class="logoImage" :src="logoImage" alt="Logo" />
      </div>
      <form class="formContainer" @submit.prevent="handleLogin">
        <div class="formGroup">
          <label for="adminLoginId" class="labelText pre-t"> 아이디 </label>
          <input
            type="text"
            id="adminLoginId"
            placeholder="아 이 디"
            class="inputText pre-t"
            v-model="adminLoginId"
          />
        </div>
        <div class="formGroup">
          <label for="adminLoginPw" class="labelText pre-t"> 비밀번호 </label>
          <input
            type="password"
            id="adminLoginPw"
            placeholder="비 밀 번 호"
            class="inputText"
            v-model="adminLoginPw"
          />
        </div>
        <div class="formSubGroup">
          <button type="submit" class="loginButton pre-t">
            로그인
            <img :src="loginIcon" alt="login_icon" class="loginIcon" />
          </button>
        </div>
        <div class="additionalOptions">
          <div class="id-save">
            <input type="checkbox" id="saveId" />
            <label for="saveId" class="pre-t" style="margin-left: 5px"
              >아이디 저장</label
            >
          </div>
          <a href="#" class="password-recovery pre-t">비밀번호 찾기</a>
        </div>
      </form>
    </div>
    <div class="welcome-message">
      <span class="samsung-sw-academy pre-bt"> 불량제로! <br /> </span>
      <span class="ssafy-welcome pre-bt">오신것을 환영합니다.</span>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ref } from "vue";
import { login } from "@/api/user.js";
import logoImage from "@/assets/Login/Logo_icon.png"; // 로고 이미지 경로
import loginIcon from "@/assets/Login/Login_icon.png"; // 로그인 아이콘 경로
import sampleImage from "@/assets/Login/Login_background_image.svg"; // 배경 이미지 경로
import { useUserStore } from "@/stores/user";

const router = useRouter();
const userStore = useUserStore();
const adminLoginId = ref("");
const adminLoginPw = ref("");

const handleLogin = () => {
  login({
    username: adminLoginId.value,
    password: adminLoginPw.value,
  })
    .then((res) => {
      userStore.loginFun();
      router.push("/home");
    })
    .catch((err) => {
      console.log(err);
      Swal.fire({
        icon: "error",
        title: "로그인에 실패했습니다.",
        text: "아이디와 비밀번호를 확인해주세요!",
      });
    });
};
</script>

<style scoped>
/* 스타일은 이전 코드 그대로 유지 */
:root {
  --default-font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    Ubuntu, "Helvetica Neue", Helvetica, Arial, "PingFang SC",
    "Hiragino Sans GB", "Microsoft Yahei UI", "Microsoft Yahei",
    "Source Han Sans CN", sans-serif;
}

.loginContainer {
  position: relative;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-image: url("@/assets/Login/Login_background_image.svg"); /* 배경 이미지 경로 */
  background-size: cover;
  background-position: center;
}

.form-wrapper {
  background-color: white;
  padding: 40px;
  border-radius: 5px;
  width: 580px;
  box-shadow: 0px 20px 25px rgba(0, 0, 0, 0.07);
  margin-left: 100px;
}

.logoImage {
  width: 230px;
  height: 90px;
  margin-bottom: 20px;
}

.formContainer {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.formGroup {
  margin-bottom: 20px;
  width: 100%;
}

.labelText {
  font-size: 16px;
  margin-bottom: 10px;
  display: block;
}

.inputText {
  width: 480px;
  height: 46px;
  padding: 10px;
  border: 1px solid #dddddd;
  border-radius: 5px;
  font-size: 16px;
  outline: none;
  box-sizing: border-box;
}

.formSubGroup {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.loginButton {
  width: 480px;
  height: 46px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
}

.loginIcon {
  width: 20px;
  margin-left: 5px;
}

.additionalOptions {
  display: flex;
  justify-content: space-between;
  width: 480px;
  margin-top: 20px;
}

.password-recovery {
  color: #777f88;
  text-decoration: none;
  font-size: 14px;
}

.id-save {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.welcome-message {
  color: white;
  text-align: left;
  padding-right: 50px;
  font-size: 40px;
  margin-right: 300px;
  margin-bottom: 200px;
}

.samsung-sw-academy {
  font-size: 40px;
  font-weight: 300;
  color: black;
}

.ssafy-welcome {
  font-size: 20px;
  font-weight: 300;
  color: black;
}
</style>
