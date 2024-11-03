import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useUserStore = defineStore(
  "user",
  () => {
    const isLogin = ref(false);

    function loginFun() {
      isLogin.value = true;
    }

    function logoutFun() {
      isLogin.value = false;
    }
    return { isLogin, loginFun, logoutFun };
  },
  { persist: true }
);
