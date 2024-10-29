import { localAxios } from "@/utils/request";

const axios = localAxios();
const baseURL = "api/v1";

export function setTargetProduction(cnt) {
  return axios({
    url: `${baseURL}/line/target`,
    method: "patch",
    params: { lineId: 1 },
    data: cnt,
    withCredentials: true,
  });
}

export function lineState() {
  return axios({
    url: `${baseURL}/line`,
    method: "get",
    params: { lineId: 1 },
    withCredentials: true,
  });
}

export function lineStart() {
  return axios({
    url: `${baseURL}/operation/start`,
    method: "put",
    params: { lineId: 1 },
    withCredentials: true,
  });
}

export function lineEnd() {
  return axios({
    url: `${baseURL}/operation/end`,
    method: "patch",
    params: { lineId: 1, cycleProdId: 5 },
    withCredentials: true,
  });
}
