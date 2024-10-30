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

// 서보 핀 배열 및 축 이름
const int servoPins[3] = {9, 6, 5};
const char* axisNames[3] = { "Base (theta1)", "Shoulder (theta2)", "Elbow (theta3)" };

const int speedLimit = 2;

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

void moveServoSmoothly(Servo &servo, int &currentAngle, int targetAngle, const char* axisName) {
  int step = (targetAngle - currentAngle) > 0 ? speedLimit : -speedLimit;
  while (abs(targetAngle - currentAngle) > speedLimit) {
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

void executeSequence(int sequenceType, int conveyorPosition) {
  const char* stateMessage;

  // 컨베이어 위치로 이동 후 전자석 활성화 시 OLED 업데이트
  if (conveyorPosition == 1) {
    setModeAngles(CONVEYOR1);
    stateMessage = "Conveyor 1";
  } else if (conveyorPosition == 2) {
    setModeAngles(CONVEYOR2);
    stateMessage = "Conveyor 2";
  }
  updateOLED(stateMessage, false); // 각 단계 시작 시 OLED 업데이트
  moveToTargetAngles();

  // 전자석 활성화 및 OLED 업데이트
  activateElectromagnet();
  updateOLED(stateMessage, true);
  delay(300);

  // 거점으로 역순 이동
  setModeAngles(WAYPOINT);
  updateOLED("Waypoint", true);
  moveToTargetAngles(true);  // 역방향 이동
  delay(150);

  // 재활용 또는 불량 위치로 이동 전 OLED 업데이트
  setModeAngles(sequenceType == 1 ? RECYCLE : DEFECTIVE);
  stateMessage = (sequenceType == 1) ? "Recycle" : "Defective";
  updateOLED(stateMessage, true);
  moveToTargetAngles();
  delay(250);

  // 전자석 비활성화 후 대기 상태로 전환
  deactivateElectromagnet();
  updateOLED("Standby", false);
  delay(250);
  setModeAngles(STANDBY);
  moveToTargetAngles(true);  // 역방향 이동
}
