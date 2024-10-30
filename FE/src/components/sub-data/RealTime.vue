<script setup>
import Log from "@/components/sub-data/Log.vue";
import MachineOperation from "@/components/sub-data/MachineOperation.vue";
import { useOperateStore } from "@/stores/operate";
import { useCounterStore } from "@/stores/counter";
import { useEnvStore } from "@/stores/environment";

const operateStore = useOperateStore();
const cntStore = useCounterStore();
const envStore = useEnvStore();
// 오늘 날짜 설정
const today = new Date();
const startDate = formatDate(today);
const endDate = formatDate(today);
// SSE 연결
const eventSource = new EventSource(
  // "http://k11e202.p.ssafy.io:8081/api/v1/realtime/prod?lineId=1",
  "https://k11e202.p.ssafy.io/api/v1/realtime/prod?lineId=1",
  {}
);

// 실시간 데이터 수신 (realtimeProd 이벤트)
eventSource.addEventListener("realtimeProd", function (event) {
  const data = JSON.parse(event.data);
  // console.log(data);
  cntStore.sseData(data);
});

// SSE 연결 에러 처리
eventSource.onerror = function (event) {
  // console.error("SSE connection error:", event);
};

// SSE for Environment Data
const environmentEventSource = new EventSource(
  // "http://k11e202.p.ssafy.io:8081/api/v1/realtime/circumstance?lineId=1"
  "https://k11e202.p.ssafy.io/api/v1/realtime/circumstance?lineId=1"
);

environmentEventSource.addEventListener(
  "realtimeCircumstance",
  function (event) {
    const data = JSON.parse(event.data);
    envStore.sseData(data);
    // console.log(data);
  }
);

environmentEventSource.onerror = function (event) {
  // console.error("SSE connection error (Environment):", event);
};

function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}
</script>

<template>
  <div class="real-time-con con shadow">
    <div class="box-row title-con">
      <img src="@/assets/img/log.svg" alt="실시간 " />
      <span class="title pre-bt">실시간 정보</span>
      <div class="button b2" id="button-10">
        <input
          type="checkbox"
          class="checkbox"
          :checked="!operateStore.machineOnOff"
          @click="operateStore.machineOnOffFun()"
        />
        <div class="knobs">
          <span>ON</span>
        </div>
        <div class="layer"></div>
      </div>
    </div>
    <Log />
    <MachineOperation />
  </div>
</template>

<style scoped>
.real-time-con {
  width: 800px;
  height: 100%;

  padding: 10px 20px;

  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-color: red; */
}
.title-con {
  width: 100%;
  position: relative;
}
.title {
  font-size: 25px;
  font-weight: 900;
}

.button-cover {
  height: 100px;
  /* margin: 20px; */
  background-color: #fff;
  box-shadow: 0 10px 20px -8px #c5d6d6;
  border-radius: 4px;
}

.button-cover:before {
  counter-increment: button-counter;
  content: counter(button-counter);
  position: absolute;
  right: 0;
  bottom: 0;
  color: #d7e3e3;
  font-size: 12px;
  line-height: 1;
  padding: 5px;
}

.button-cover,
.knobs,
.layer {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 2px;
  left: 0;
}

.button {
  position: absolute;
  bottom: 15px;
  right: 5px;
  width: 124px;
  height: 40px;

  overflow: hidden;
}

.button.r,
.button.r .layer {
  border-radius: 100px;
}

.button.b2 {
  border-radius: 2px;
}

.checkbox {
  position: relative;
  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;
  opacity: 0;
  cursor: pointer;
  z-index: 3;
}

.knobs {
  z-index: 2;
}

.layer {
  width: 100%;
  background-color: #ebf7fc;
  transition: 0.3s ease all;
  z-index: 1;
}

/* Button 10 */
#button-10 .knobs:before,
#button-10 .knobs:after,
#button-10 .knobs span {
  position: absolute;
  top: 3px;
  width: 49px;
  height: 14px;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  line-height: 1;
  padding: 9px 4px;
  border-radius: 2px;
  transition: 0.3s ease all;
}

#button-10 .knobs:before {
  content: "";
  left: 4px;
  background-color: #03a9f4;
}

#button-10 .knobs:after {
  content: "OFF";
  top: 0px;
  right: 4px;
  color: #4e4e4e;
}

#button-10 .knobs span {
  display: inline-block;
  left: 7px;
  top: 0px;
  color: #fff;
  z-index: 1;
}

#button-10 .checkbox:checked + .knobs span {
  color: #4e4e4e;
}

#button-10 .checkbox:checked + .knobs:before {
  left: 63px;
  background-color: #f44336;
}

#button-10 .checkbox:checked + .knobs:after {
  color: #fff;
}

#button-10 .checkbox:checked ~ .layer {
  background-color: #fcebeb;
}
</style>
