<template>
  <div class="loginContainer">
    <div class="form-wrapper">
      <div>
        <img class="logoImage" :src="logoImage" alt="Logo" />
      </div>
      <form class="formContainer" @submit.prevent="handleLogin">
        <div class="formGroup">
          <label for="adminLoginId" class="labelText">
            아이디
          </label>
          <input 
            type="text" 
            id="adminLoginId" 
            placeholder="아 이 디" 
            class="inputText" 
            v-model="adminLoginId"
          />
        </div>
        <div class="formGroup">
          <label for="adminLoginPw" class="labelText">
            비밀번호
          </label>
          <input 
            type="password" 
            id="adminLoginPw" 
            placeholder="비 밀 번 호" 
            class="inputText" 
            v-model="adminLoginPw"
          />
        </div>
        <div class="formSubGroup">
          <button type="submit" class="loginButton">
            로그인
            <img :src="loginIcon" alt="login_icon" class="loginIcon" />
          </button>
        </div>
        <div class="additionalOptions">
          <div class="id-save">
            <input type="checkbox" id="saveId" />
            <label for="saveId">아이디 저장</label>
          </div>
          <a href="#" class="password-recovery">비밀번호 찾기</a>
        </div>
      </form>
    </div>
    <div class="welcome-message">
      <span class="samsung-sw-academy">
        불량제로! <br />
      </span>
      <span class="ssafy-welcome">오신것을 환영합니다.</span>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import logoImage from '@/assets/Login/Logo_icon.png'; // 로고 이미지 경로
import loginIcon from '@/assets/Login/Login_icon.png'; // 로그인 아이콘 경로
import sampleImage from '@/assets/Login/Login_background_image.svg'; // 배경 이미지 경로

const router = useRouter();
const adminLoginId = ref('');
const adminLoginPw = ref('');

const handleLogin = () => {
  // 로그인 로직 (아이디와 비밀번호 확인)
  // 만약 로그인이 성공하면 다음 경로로 이동
  if (adminLoginId.value && adminLoginPw.value) {
    router.push('/home');
  } else {
    alert('아이디와 비밀번호를 입력하세요.');
  }
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
  background-image: url('@/assets/Login/Login_background_image.svg'); /* 배경 이미지 경로 */
  background-size: cover;
  background-position: center;
}

.form-wrapper {
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  width: 580px;
  box-shadow: 0px 20px 25px rgba(0, 0, 0, 0.07);
  margin-left: 100px;
}

.logoImage {
  width: 139px;
  height: 100px;
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
  font-size: 14px;
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
