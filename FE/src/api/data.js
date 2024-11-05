import { localAxios } from "@/utils/request";

const axios = localAxios();

export function setTargetProduction(cnt) {
  return axios({
    url: `line/target`,
    method: "patch",
    params: { lineId: 1 },
    data: cnt,
    withCredentials: true,
  });
}

export function lineState() {
  return axios({
    url: `line`,
    method: "get",
    params: { lineId: 1 },
    withCredentials: true,
  });
}

export function dailyProduct(date) {
  return axios({
    url: `daily/prod`,
    method: "get",
    params: date,
    withCredentials: true,
  });
}

export function weeklyProduct() {
  return axios({
    url: `weekly/prod`,
    method: "get",
    params: { lineId: 1 },
    withCredentials: true,
  });
}

export function lineStart() {
  return axios({
    url: `operation/start`,
    method: "put",
    params: { lineId: 1 },
    withCredentials: true,
  });
}

export function lineEnd() {
  return axios({
    url: `operation/end`,
    method: "patch",
    params: { lineId: 1, cycleProdId: 5 },
    withCredentials: true,
  });
}
