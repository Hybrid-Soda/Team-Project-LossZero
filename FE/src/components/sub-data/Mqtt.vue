<script setup>
import { useEnvStore } from "@/stores/environment";
import { useLogStore } from "@/stores/logdata";
import { useOperateStore } from "@/stores/operate";
import { watch } from "vue";

var host = "k11e202.p.ssafy.io";
var port = 443; // port 변수 제거
var mqtt;

const logStore = useLogStore();
const operateStore = useOperateStore();
const envStore = useEnvStore();

watch(
  () => operateStore.machineOnOff,
  () => {
    if (operateStore.machineOnOff) {
      Swal.fire({
        icon: "warning",
        title: "작동 중지 하시겠습니까?",
        text: "잦은 중지는 손실을 초래합니다.",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#429f50",
        cancelButtonColor: "#d33",
      }).then((result) => {
        if (result.isConfirmed) {
          operateStore.machineOff();
          // 연결되어 있지 않다면 연결 시도
          if (!mqtt.isConnected()) {
            MQTTConnect();
          }

          sendMsg(
            `{ "sender": "web", "lineId": 1, "message": "${
              operateStore.machineOnOff ? "off" : "on"
            }" }`
          );
        } else if (result.isDismissed) {
          operateStore.machineOn();
        }
      });
    } else {
      operateStore.machineOn();

      // 연결되어 있지 않다면 연결 시도
      if (!mqtt.isConnected()) {
        MQTTConnect();
      }

      sendMsg(
        `{ "sender": "web", "lineId": 1, "message": "${
          operateStore.machineOnOff ? "off" : "on"
        }" }`
      );
    }
  }
);

function messageSeparate(message) {
  try {
    // JSON 문자열을 객체로 변환
    const data = JSON.parse(message);
    // sender가 원하는 sender인지 확인하고 결과 반환
    return data;
  } catch (error) {
    // JSON 파싱 오류가 발생하면 false 반환
    console.error("Invalid JSON format:", error);
    return false;
  }
}

// callback함수 - 접속 성공
function onConnect() {
  console.log("접속완료");
}

// callback함수 - 접속 실패
function onFailure() {
  console.log("접속실패");
}

// publish 함수
function sendMsg(msg) {
  // alert(msg);
  let message;
  message = new Paho.MQTT.Message(msg);
  message.destinationName = "realtime-oper";
  mqtt.send(message);
}

// 메시지 수신 콜백 함수
function onMessageArrived(message) {
  const subMessage = message.payloadString;
  const data = messageSeparate(subMessage);
  console.log(data);
  const sender = data.sender;

  if (sender === "arm") {
    const status = data.status;

    if (status === "complete") {
      operateStore.armOff();
      operateStore.coveyerOn();
    }
    // console.log(status);
  } else if (sender === "camera") {
    const status = data.status;

    operateStore.cameraOff();

    if (status === "defective" || status === "reusable") {
      operateStore.armOn();
    } else if (status === "normal") {
      operateStore.coveyerOn();
    } else if (status === "error") {
      operateStore.machineOff();
      Swal.fire({
        icon: "error",
        title: "시스템 Error",
        text: "공장 상태를 확인해주세요",
      });
    }
  } else if (sender === "web") {
    const onOff = data.message;

    if (onOff === "on") {
      operateStore.coveyerOn();
      operateStore.armOff();
      operateStore.cameraOff();
    } else if (onOff === "off") {
      operateStore.coveyerOff();
      operateStore.armOff();
      operateStore.cameraOff();
    }
  } else if (sender === "belt") {
    const status = data.status;

    if (status === "off") {
      operateStore.cameraOn();
      operateStore.coveyerOff();
      operateStore.armOff();
    } else if (status === "on") {
      operateStore.coveyerOn();
      operateStore.cameraOff();
      operateStore.armOff();
    }
  } else if (sender === "raspberry-pi") {
    if (data["circumstance"]) {
      envStore.mqttData(data["circumstance"]);
    } else {
      logStore.createIssue();
    }
  }
}

// subscribe 함수
function subscribe(topic) {
  mqtt.subscribe(topic, {
    onSuccess: function () {
      console.log("구독 성공: " + topic);
    },
    onFailure: function () {
      console.log("구독 실패: " + topic);
    },
  });
}

//mqtt 통신을 관리하기 위한 사용자 정의 함수
function MQTTConnect() {
  const clientId =
    "javascript_client" + Math.floor(Math.random() * (10 - 0) + 1);
  mqtt = new Paho.MQTT.Client(host, port, clientId);
  mqtt.onMessageArrived = onMessageArrived;
  mqtt.onConnectionLost = onConnectionLost;
  var options = {
    timeout: 3,
    useSSL: true,
    keepAliveInterval: 60, // 여기에 원하는 초 단위로 설정합니다.
    onSuccess: function () {
      onConnect();
      subscribe("realtime-oper");
      subscribe("realtime-prod");
      subscribe("realtime-circ");
      subscribe("realtime-cycle");
    },
    onFailure: onFailure,
  };

  mqtt.connect(options);
}

function onConnectionLost(responseObject) {
  if (responseObject.errorCode !== 0) {
    console.log("연결 끊김: " + responseObject.errorMessage);
    setTimeout(MQTTConnect, 3000); // 3초 후에 다시 연결 시도
  }
}

MQTTConnect();
</script>

<template>
  <div></div>
</template>

<style scoped></style>
