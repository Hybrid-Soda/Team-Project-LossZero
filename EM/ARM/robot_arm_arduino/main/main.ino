#include "ServoControl.h"
#include "ElectromagnetControl.h"

// 초기 설정 함수
void setup() {
  Serial.begin(9600);
  initializeServos();
  initializeElectromagnet();
  moveToStandby();
  Serial.println("Setup complete.");
}

// 루프 함수
void loop() {
  executeSequence(1); // 첫 번째 시퀀스
  delay(2000); // 2초 대기

  executeSequence(2); // 두 번째 시퀀스
  delay(2000); // 2초 대기
}
