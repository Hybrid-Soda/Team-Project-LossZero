// Arduino A Code

#include <SoftwareSerial.h>

SoftwareSerial mySerial(10, 11); // RX, TX


void setup() {
  Serial.begin(9600); // 디버깅용 시리얼
  mySerial.begin(9600); // 아두이노 간 통신용 시리얼
  Serial.println("Arduino A ready.");
  delay(5000); //5초 대기 후 시작
}

void loop() {
  sendCommand("Command1");
  waitForCompletion();
  delay(2000); // 2초 대기 후 다시 시작

  sendCommand("Command2");
  waitForCompletion();
  delay(2000); // 2초 대기 후 다시 시작

  sendCommand("Command3");
  waitForCompletion();
  delay(2000); // 2초 대기 후 다시 시작

  sendCommand("Command4");
  waitForCompletion();
  delay(2000); // 2초 대기 후 다시 시작
}

void sendCommand(String command) {
  mySerial.println(command);
  Serial.println("Sent: " + command);
}

void waitForCompletion() {
  while (true) {
    if (mySerial.available()) {
      String message = mySerial.readStringUntil('\n');
      message.trim();
      Serial.println("Received: " + message);
      if (message.endsWith("Complete")) {
        break; // 완료 메시지 수신 시 루프 탈출
      }
    }
  }
}
