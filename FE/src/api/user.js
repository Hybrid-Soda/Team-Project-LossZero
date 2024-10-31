import { localAxios } from "@/utils/request";

const axios = localAxios();

export function login(userInfo) {
  return axios({
    // url: `/login`,
    url: `/api/v1/login`,
    method: "post",
    data: userInfo,
    withCredentials: true,
  });
}
