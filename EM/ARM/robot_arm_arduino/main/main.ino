// main.ino

#include "ServoControl.h"
#include "ElectromagnetControl.h"
#include "OLED.h"

// 초기 설정 함수
void setup() {
  Serial.begin(9600);
  initializeServos();
  initializeElectromagnet();
  initializeOLED();
  moveToStandby();
  Serial.println("Setup complete.");
}

// 루프 함수
void loop() {
  executeSequence(1, 1); // 1번 시퀀스, 컨베이어 1번 위치
  delay(2000); // 2초 대기

  executeSequence(1, 2); // 1번 시퀀스, 컨베이어 2번 위치
  delay(2000); // 2초 대기

  executeSequence(2, 1); // 2번 시퀀스, 컨베이어 1번 위치
  delay(2000); // 2초 대기

  executeSequence(2, 2); // 2번 시퀀스, 컨베이어 2번 위치
  delay(2000); // 2초 대기
}
