import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
  });

  // // 요청 인터셉터 추가
  // instance.interceptors.request.use(
  //   (config) => {
  //     const token = localStorage.getItem("accessToken");
  //     if (token) {
  //       config.headers["Authorization"] = `Bearer ${token}`;
  //     }
  //     return config;
  //   },
  //   (error) => {
  //     return Promise.reject(error);
  //   }
  // );

  instance.defaults.headers.common["Authorization"] = "";
  instance.defaults.headers.post["Content-Type"] = "application/json";
  instance.defaults.headers.put["Content-Type"] = "application/json";
  instance.defaults.headers.patch["Content-Type"] = "application/json";
  return instance;
}

export { localAxios };
