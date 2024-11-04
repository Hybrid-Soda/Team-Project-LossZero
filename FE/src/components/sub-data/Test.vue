<script setup>
var host = "k11e202.p.ssafy.io";
var port = 9001;
var mqtt;

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
  alert(msg);
  message = new Paho.MQTT.Message(msg);
  message.destinationName = "test";
  mqtt.send(message);
}

// 메시지 수신 콜백 함수
function onMessageArrived(message) {
  console.log("수신된 메시지: " + message.payloadString);
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
  console.log("mqtt 접속 : " + host + ", " + port);
  mqtt = new Paho.MQTT.Client(host, port, "javascript_client");

  mqtt.onMessageArrived = onMessageArrived;

  var options = {
    timeout: 3,
    useSSL: true,
    onSuccess: function () {
      onConnect();
      subscribe("realtime-oper");
      subscribe("realtime-prod");
      subscribe("realtime-circ");
    },
    onFailure: onFailure,
  };
  mqtt.connect(options);
}

MQTTConnect();
</script>

<template>
  <div></div>
</template>

<style scoped></style>
