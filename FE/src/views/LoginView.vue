<template>
  <div class="loginContainer" :style="{ backgroundImage: `url(${sampleImage})` }">
    <div>
      <img class="logoImage" :src="logoImage" alt="Logo" />
    </div>
    <form class="formContainer" @submit.prevent="login"> <!-- prevent 사용 -->
      <div class="formGroup">
        <label for="adminLoginId" class="labelText">
          아이디
          <img :src="idIcon" alt="id_icon" class="labelIcon" />
        </label>
        <input
          type="text"
          id="adminLoginId"
          placeholder="아 이 디"
          class="inputText"
        />
      </div>
      <div class="formGroup">
        <label for="adminLoginPw" class="labelText">
          비밀번호
          <img :src="passwordIcon" alt="password_icon" class="labelIcon" />
        </label>
        <input
          type="password"
          id="adminLoginPw"
          placeholder="비 밀 번 호"
          class="inputText"
        />
      </div>
      <div class="formSubGroup">
        <div></div>
        <button type="submit" class="loginButton">
          로그인
          <img :src="loginIcon" alt="login_icon" class="loginIcon" />
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'; // Vue Router 임포트
import sampleImage from '@/assets/Login/Login_background_image.png';
import logoImage from '@/assets/Login/Logo_icon.png';
import idIcon from '@/assets/Login/Id_icon.png';
import passwordIcon from '@/assets/Login/Password_icon.png';
import loginIcon from '@/assets/Login/Login_icon.png';


const router = useRouter();

function login() {
  router.push({ name: 'home'})
}

</script>

<style scoped>
.loginContainer {
  background-size: cover;
  background-position: center;
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

.labelText {
  font-size: 17px;
  font-weight: bold;
}

.labelIcon {
  width: 23px;
  height: 23px;
  padding-bottom: 5px;
  margin-left: 4px;
  vertical-align: middle;
}

.loginIcon {
  width: 30px;
}

.logoImage {
  width: 350px;
  height: auto;
  /* padding-left: 110px; */
  margin-bottom: 15px;
}

.formContainer {
  padding: 5px 120px;
  padding-bottom: 100px;
  border-radius: 10px;
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.formGroup {
  margin-bottom: 20px;
  width: 100%;
}

.formGroup label {
  display: block;
  margin-bottom: 5px;
}

.formGroup input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.formSubGroup {
  display: flex;
  justify-content: space-between;
  width: 100%;
  justify-content: center;
}

.loginButton {
  padding: 4px 8px;
  padding-left: 20px;
  background-color: #b0d2fa;
  color: black;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  display: flex;
  align-items: center;
  font-size: 17px;
  font-weight: bold;
  margin-top: 10px;
}

.loginButton:hover {
  background-color: #3f95fa;
}

.inputText {
  height: 45px;
  background-color: whitesmoke;
}

.inputText:focus {
  background-color: white;
}
</style>
