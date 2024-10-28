#include <Servo.h>

// =========================
// 서보 모터 설정 및 제어 함수
// =========================

// 서보 모터 객체 생성
Servo servo1; // 베이스 회전 (theta1)
Servo servo2; // 어깨 관절 (theta2)
Servo servo3; // 팔꿈치 관절 (theta3)

// 서보 모터 핀 번호
const int servoPin1 = 9;
const int servoPin2 = 6;
const int servoPin3 = 5;

// 가변저항 핀 번호
const int potPins[4] = {A0, A1, A2, A3}; // 1번부터 4번까지의 가변저항

// 서보 모터 이동 속도 제한
const int speedLimit = 1; // 각도 변화량 제한 (1도)

// 서보 모터를 부드럽게 개별적으로 이동시키는 함수
void moveServoSmoothly(Servo &servo, int &currentAngle, int targetAngle, const char* axisName) {
  // Serial.print(axisName);
  // Serial.print(" is moving from ");
  // Serial.print(currentAngle);
  // Serial.print(" degrees to ");
  // Serial.print(targetAngle);
  // Serial.println(" degrees.");

  int step = (targetAngle - currentAngle) > 0 ? speedLimit : -speedLimit;
  while (abs(targetAngle - currentAngle) > speedLimit) {
    currentAngle += step;
    servo.write(currentAngle);

    // 이동 중인 각도 출력
    // Serial.print("  ");
    // Serial.print(axisName);
    // Serial.print(" current angle: ");
    // Serial.println(currentAngle);

    delay(10);
  }
  
  servo.write(targetAngle);
  currentAngle = targetAngle; // 최종 위치 업데이트
  Serial.print(axisName);
  Serial.print(" reached target angle: ");
  Serial.println(targetAngle);
}

// ==========================
// 메인 프로그램 시작 부분
// ==========================

// 현재 관절 각도 (도 단위)
int theta_current[3] = {80, 70, 180};

// 목표 각도 (모드에 따라 설정됨)
int theta_target[3];

// 각 모드에 따른 목표 각도 설정
void setModeAngles(int mode) {
  switch (mode) {
    case 0: // 컨베이어 (Conveyor)
      theta_target[0] = 24;
      theta_target[1] = 53;
      theta_target[2] = 180;
      Serial.println("Conveyor mode selected.");
      break;
    case 1: // 대기 (Standby)
      theta_target[0] = 80;
      theta_target[1] = 70;
      theta_target[2] = 180;
      Serial.println("Standby mode selected.");
      break;
    case 2: // 재활용 (Recycle)
      theta_target[0] = 127;
      theta_target[1] = 40;
      theta_target[2] = 170;
      Serial.println("Recycle mode selected.");
      break;
    case 3: // 불량 (Defective)
      theta_target[0] = 160;
      theta_target[1] = 30;
      theta_target[2] = 160;
      Serial.println("Defective mode selected.");
      break;
  }
}

void setup() {
  Serial.begin(9600);

  // 서보 모터 초기화
  servo1.attach(servoPin1);
  servo2.attach(servoPin2);
  servo3.attach(servoPin3);

  // 초기 위치로 서보 모터 이동
  servo1.write(theta_current[0]);
  servo2.write(theta_current[1]);
  servo3.write(theta_current[2]);

  Serial.println("Setup complete.");
}

void loop() {
  int potValues[4];
  int maxPotValue = 0;
  int selectedMode = 1; // 기본 모드: 대기 (Standby)

  // 가변저항 값 읽기
  for (int i = 0; i < 4; i++) {
    potValues[i] = analogRead(potPins[i]);
    Serial.print("Potentiometer ");
    Serial.print(i + 1);
    Serial.print(": ");
    Serial.println(potValues[i]);

    // 최대값이 500 이상인 가변저항의 모드를 선택
    if (potValues[i] > 500 && potValues[i] > maxPotValue) {
      maxPotValue = potValues[i];
      selectedMode = i; // 가장 높은 값을 가진 가변저항의 인덱스를 모드로 설정
    }
  }

  // 선택된 모드에 따라 목표 각도 설정
  Serial.print("Mode activated by potentiometer ");
  Serial.println(selectedMode + 1);
  setModeAngles(selectedMode);

  // 개별적으로 서보 모터 이동
  moveServoSmoothly(servo1, theta_current[0], theta_target[0], "Base (theta1)");
  moveServoSmoothly(servo2, theta_current[1], theta_target[1], "Shoulder (theta2)");
  moveServoSmoothly(servo3, theta_current[2], theta_target[2], "Elbow (theta3)");

  // 현재 각도 출력
  Serial.println("Updated current angles:");
  Serial.print("  Base (theta1): ");
  Serial.println(theta_current[0]);
  Serial.print("  Shoulder (theta2): ");
  Serial.println(theta_current[1]);
  Serial.print("  Elbow (theta3): ");
  Serial.println(theta_current[2]);
  Serial.println("-------------------------------------------");
  Serial.println("");

  delay(2000); // 2초 대기 후 다시 체크
}
