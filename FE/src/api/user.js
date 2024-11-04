import { localAxios } from "@/utils/request";

const axios = localAxios();

export function login(userInfo) {
  return axios({
    url: `login`,
    method: "post",
    data: userInfo,
    withCredentials: true,
  });
}

export function logout() {
  return axios({
    url: `logout`,
    method: "post",
    // withCredentials: true,
  });
}
