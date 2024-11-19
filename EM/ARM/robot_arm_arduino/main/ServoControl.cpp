// ServoControl.cpp

#include "ServoControl.h"
#include "ElectromagnetControl.h"
#include "OLED.h"
#include <Arduino.h>

// 모드별 각도 설정을 담은 구조체
struct ModeAngles {
  float theta[3];
  const char* message;
};

Servo servos[3];
float theta_current[3] = {80.0, 60.0, 0.0};
float theta_target[3];

const int potPin = A0; // 가변저항 핀 (A0)
float speed = 1.0;         // 기본 속도는 1.0 (1~3의 값으로 조절)

// 서보 핀 배열 및 축 이름
const int servoPins[3] = {9, 6, 5};
const char* axisNames[3] = { "Base (theta1)", "Shoulder (theta2)", "Elbow (theta3)" };

// 모드별 각도 및 메시지
const ModeAngles modes[] = {
  { {8.5, 20.0, 120.25}, "Conveyor1-1" },
  { {18.5, 20.0, 120.25}, "Conveyor1-2" },
  { {8.5, 50.0, 180.0}, "Conveyor2-1" },
  { {18.5, 50.0, 180.0}, "Conveyor2-2" },
  { {80.0, 60.0, 0.0},   "Standby" },
  { {125.0, 35.0, 180.0}, "Recycle" },
  { {170.0, 35.0, 180.0}, "Defective" },
  { {100.0, 70.0, 50.0}, "Waypoint" }
};

void initializeServos() {
  for (int i = 0; i < 3; i++) {
    servos[i].attach(servoPins[i]);
  }
}

// 가변저항을 통해 속도 설정 함수
void updateSpeedFromPot() {
  int potValue = analogRead(potPin); // 가변저항 값 읽기
  if (potValue < 341) {
    speed = 1.0; // 속도 1
  } else if (potValue < 682) {
    speed = 2.0; // 속도 2
  } else {
    speed = 3.0; // 속도 3
  }
  Serial.print("Speed set to: ");
  Serial.println(speed);
}

// 서보 모터를 부드럽게 개별적으로 이동시키는 함수
void moveServoSmoothly(Servo &servo, float &currentAngle, float targetAngle, const char* axisName) {
  float step = (targetAngle - currentAngle) > 0 ? speed : -speed;
  while (abs(targetAngle - currentAngle) > speed) {
    currentAngle += step;
    servo.write(static_cast<int>(round(currentAngle)));
    delay(15);
  }
  servo.write(static_cast<int>(round(targetAngle)));
  currentAngle = targetAngle;
  Serial.print(axisName);
  Serial.print(" reached target angle: ");
  Serial.println(targetAngle, 1);
}

void setModeAngles(Mode mode) {
  for (int i = 0; i < 3; i++) {
    theta_target[i] = modes[mode].theta[i];
  }
  Serial.println(modes[mode].message);
}

void moveToTargetAngles(bool reverse = false) {
  if (!reverse) {
    for (int i = 0; i < 3; i++) {  // 정방향 이동
      moveServoSmoothly(servos[i], theta_current[i], theta_target[i], axisNames[i]);
    }
  } else {
    for (int i = 2; i >= 0; i--) {  // 역순으로 이동
      moveServoSmoothly(servos[i], theta_current[i], theta_target[i], axisNames[i]);
    }
  }
}

void moveToStandby() {
  setModeAngles(STANDBY);
  moveToTargetAngles();
}

// 특정 모드로 이동하면서 OLED 업데이트
void moveAndDisplay(Mode mode, const char* message, bool electromagnetOn, bool reverse = false) {
  setModeAngles(mode);
  updateSpeedFromPot(); // 가변저항을 통해 속도 업데이트
  updateOLED(message, electromagnetOn, speed); // speed 값을 전달하여 OLED에 출력
  moveToTargetAngles(reverse);
}

// 전자석 상태를 설정하고 OLED 업데이트
void setElectromagnetAndDisplay(bool activate, const char* message) {
  if (activate) {
    activateElectromagnet();
  } else {
    deactivateElectromagnet();
  }
  updateOLED(message, activate, speed); // speed 값을 전달하여 OLED에 출력
}

void executeSequence(int sequenceType, int conveyorPosition) {
  Mode modeIndex1 = (conveyorPosition == 1) ? CONVEYOR1_1 : CONVEYOR2_1;
  Mode modeIndex2 = (conveyorPosition == 1) ? CONVEYOR1_2 : CONVEYOR2_2;

  const char* stateMessage1 = (conveyorPosition == 1) ? "Conveyor 1-1" : "Conveyor 2-1";
  const char* stateMessage2 = (conveyorPosition == 1) ? "Conveyor 1-2" : "Conveyor 2-2";

  float prevSpeed = speed;
  // 첫 번째 위치로 이동 후 OLED 업데이트 및 전자석 활성화
  moveAndDisplay(modeIndex1, stateMessage1, true);
  setElectromagnetAndDisplay(true, stateMessage1);
  delay(200);
  speed = 0.1;

  // 두 번째 위치로 이동 후 OLED 업데이트
  moveServoSmoothly(servos[0], theta_current[0], modes[modeIndex2].theta[0], axisNames[0]);
  delay(200);

  speed = prevSpeed;

  // 거점으로 역순 이동 후 OLED 업데이트
  moveAndDisplay(WAYPOINT, "Waypoint", true, true);
  delay(150);

  // 재활용 또는 불량 위치로 이동 후 OLED 업데이트
  const char* finalStateMessage = (sequenceType == 1) ? "Recycle" : "Defective";
  moveAndDisplay((sequenceType == 1) ? RECYCLE : DEFECTIVE, finalStateMessage, true);
  delay(250);

  // 전자석 비활성화 후 대기 상태로 OLED 업데이트
  setElectromagnetAndDisplay(false, "Standby");
  delay(250);
  moveAndDisplay(STANDBY, "Standby", false, true);
}
