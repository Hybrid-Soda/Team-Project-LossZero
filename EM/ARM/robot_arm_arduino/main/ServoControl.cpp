// ServoControl.cpp

#include "ServoControl.h"
#include "ElectromagnetControl.h"
#include "OLED.h"
#include <Arduino.h>

// 모드별 각도 설정을 담은 구조체
struct ModeAngles {
  int theta[3];
  const char* message;
};

Servo servos[3];
int theta_current[3] = {80, 40, 0};
int theta_target[3];

const int potPin = A0; // 가변저항 핀 (A0)
int speed = 1;         // 기본 속도는 1 (1~3의 값으로 조절)

// 서보 핀 배열 및 축 이름
const int servoPins[3] = {9, 6, 5};
const char* axisNames[3] = { "Base (theta1)", "Shoulder (theta2)", "Elbow (theta3)" };

// 모드별 각도 및 메시지
const ModeAngles modes[] = {
  { {20, 10, 120}, "Conveyor1" },
  { {20, 50, 180}, "Conveyor2" },
  { {80, 40, 0},   "Standby" },
  { {127, 35, 170}, "Recycle" },
  { {160, 35, 170}, "Defective" },
  { {100, 50, 80}, "Waypoint" }
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
    speed = 1; // 속도 1
  } else if (potValue < 682) {
    speed = 2; // 속도 2
  } else {
    speed = 3; // 속도 3
  }
  Serial.print("Speed set to: ");
  Serial.println(speed);
}

// 서보 모터를 부드럽게 개별적으로 이동시키는 함수
void moveServoSmoothly(Servo &servo, int &currentAngle, int targetAngle, const char* axisName) {
  int step = (targetAngle - currentAngle) > 0 ? speed : -speed;
  while (abs(targetAngle - currentAngle) > speed) {
    currentAngle += step;
    servo.write(currentAngle);
    delay(15);
  }
  servo.write(targetAngle);
  currentAngle = targetAngle;
  Serial.print(axisName);
  Serial.print(" reached target angle: ");
  Serial.println(targetAngle);
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
  // 컨베이어 위치에 따라 상태 메시지를 설정
  const char* stateMessage = (conveyorPosition == 1) ? "Conveyor 1" : "Conveyor 2";

  // 컨베이어 위치로 이동 후 전자석 비활성 상태로 OLED 업데이트
  moveAndDisplay((conveyorPosition == 1) ? CONVEYOR1 : CONVEYOR2, stateMessage, false);

  // 전자석 활성화 후 OLED 업데이트
  setElectromagnetAndDisplay(true, stateMessage);
  delay(300);

  // 거점으로 역순 이동 후 OLED 업데이트
  moveAndDisplay(WAYPOINT, "Waypoint", true, true);
  delay(150);

  // 재활용 또는 불량 위치로 이동 후 OLED 업데이트
  stateMessage = (sequenceType == 1) ? "Recycle" : "Defective";
  moveAndDisplay((sequenceType == 1) ? RECYCLE : DEFECTIVE, stateMessage, true);
  delay(250);

  // 전자석 비활성화 후 대기 상태로 OLED 업데이트
  setElectromagnetAndDisplay(false, "Standby");
  delay(250);
  moveAndDisplay(STANDBY, "Standby", false, true);
}
