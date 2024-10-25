#include <Servo.h>
#include <Math.h>

// =========================
// 서보 모터 설정 및 제어 함수
// =========================

// 서보 모터 객체 생성
Servo servo1;
Servo servo2;
Servo servo3;

// 서보 모터 핀 번호
const int servoPin1 = 9;
const int servoPin2 = 6;
const int servoPin3 = 5;

// 서보 모터 이동 속도 제한
const float speedLimit = 1.0; // 각도 변화량 제한 (1도)

// 서보 모터를 부드럽게 이동시키는 함수
void moveServoSmoothly(Servo &servo, float currentAngle, float targetAngle) {
  float step = (targetAngle - currentAngle) > 0 ? speedLimit : -speedLimit;
  while (abs(targetAngle - currentAngle) > speedLimit) {
    currentAngle += step;
    servo.write(currentAngle);
    delay(20);
  }
  servo.write(targetAngle);
}

// ==============================
// 역기구학 계산을 위한 설정 및 함수
// ==============================

// 로봇 팔의 링크 길이 (단위: mm)
const float L1 = 96.0;  // 링크1 길이
const float L2 = 65.0;  // 링크2 길이
const float L3 = 140.0; // 링크3 길이

// 최대 반복 횟수 및 허용 오차
const int MAX_ITERATIONS = 20;
const float TOLERANCE = 0.5; // mm 단위
const float ALPHA = 0.1;     // 스텝 사이즈

// 전방 기구학 계산 함수 선언
void forwardKinematics(float theta[], float &x, float &y, float &z);

// 자코비안 전치 계산 함수 선언
void computeJacobianTranspose(float theta[], float dx, float dy, float dz, float Jt[]);

// 역기구학 계산 함수 (자코비안 전치 방법)
bool inverseKinematics_JacobianTranspose(float x_target, float y_target, float z_target, float theta_current[], float theta_solution[]) {
  // 관절 각도 초기화 (라디안 단위)
  float theta[3];
  theta[0] = radians(theta_current[0]);
  theta[1] = radians(theta_current[1]);
  theta[2] = radians(theta_current[2]);

  for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
    // 전방 기구학으로 현재 말단 위치 계산
    float x_current, y_current, z_current;
    forwardKinematics(theta, x_current, y_current, z_current);

    // 위치 오차 계산
    float dx = x_target - x_current;
    float dy = y_target - y_current;
    float dz = z_target - z_current;
    float error = sqrt(dx * dx + dy * dy + dz * dz);

    // 허용 오차 이내인지 확인
    if (error < TOLERANCE) {
      // 각도를 도 단위로 변환하여 솔루션에 저장
      theta_solution[0] = degrees(theta[0]);
      theta_solution[1] = degrees(theta[1]);
      theta_solution[2] = degrees(theta[2]);
      return true;
    }

    // 자코비안 전치 계산
    float Jt[3]; // 자코비안 전치 벡터
    computeJacobianTranspose(theta, dx, dy, dz, Jt);

    // 관절 각도 업데이트
    theta[0] += ALPHA * Jt[0];
    theta[1] += ALPHA * Jt[1];
    theta[2] += ALPHA * Jt[2];
  }

  // 최대 반복 횟수 도달 시 수렴 실패
  return false;
}

// 전방 기구학 계산 함수
void forwardKinematics(float theta[], float &x, float &y, float &z) {
  // 관절 각도
  float t1 = theta[0];
  float t2 = theta[1];
  float t3 = theta[2];

  // 각 링크의 위치 계산
  float x1 = L1 * cos(t1);
  float y1 = L1 * sin(t1);
  float z1 = 0;

  float x2 = x1 + L2 * cos(t1 + t2);
  float y2 = y1 + L2 * sin(t1 + t2);
  float z2 = 0;

  float x3 = x2 + L3 * cos(t1 + t2 + t3);
  float y3 = y2 + L3 * sin(t1 + t2 + t3);
  float z3 = 0;

  // 말단 위치
  x = x3;
  y = y3;
  z = z3;
}

// 자코비안 전치 계산 함수
void computeJacobianTranspose(float theta[], float dx, float dy, float dz, float Jt[]) {
  // 관절 각도
  float t1 = theta[0];
  float t2 = theta[1];
  float t3 = theta[2];

  // 부분 미분 계산 (자코비안 행렬 요소)
  float dt1 = -L1 * sin(t1) - L2 * sin(t1 + t2) - L3 * sin(t1 + t2 + t3);
  float dt2 = -L2 * sin(t1 + t2) - L3 * sin(t1 + t2 + t3);
  float dt3 = -L3 * sin(t1 + t2 + t3);

  float dt1_y = L1 * cos(t1) + L2 * cos(t1 + t2) + L3 * cos(t1 + t2 + t3);
  float dt2_y = L2 * cos(t1 + t2) + L3 * cos(t1 + t2 + t3);
  float dt3_y = L3 * cos(t1 + t2 + t3);

  // 자코비안 전치 벡터 계산
  Jt[0] = dt1 * dx + dt1_y * dy;
  Jt[1] = dt2 * dx + dt2_y * dy;
  Jt[2] = dt3 * dx + dt3_y * dy;
}

// ==========================
// 메인 프로그램 시작 부분
// ==========================

// 현재 관절 각도 (도 단위)
float theta_current[3] = {90.0, 90.0, 90.0};

// 목표 좌표 (고정된 값)
float targetX = 111.0;
float targetY = 111.0;
float targetZ = 111.0;

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
  // 역기구학 계산
  float theta_solution[3];
  if (inverseKinematics_JacobianTranspose(targetX, targetY, targetZ, theta_current, theta_solution)) {
    // 서보 모터를 부드럽게 이동
    moveServoSmoothly(servo1, theta_current[0], theta_solution[0]);
    moveServoSmoothly(servo2, theta_current[1], theta_solution[1]);
    moveServoSmoothly(servo3, theta_current[2], theta_solution[2]);

    // 현재 각도 업데이트
    theta_current[0] = theta_solution[0];
    theta_current[1] = theta_solution[1];
    theta_current[2] = theta_solution[2];

    Serial.print("Moved to angles: ");
    Serial.print(theta_current[0]);
    Serial.print(", ");
    Serial.print(theta_current[1]);
    Serial.print(", ");
    Serial.println(theta_current[2]);
  } else {
    Serial.println("Inverse kinematics calculation failed.");
  }

  delay(1000); // 1초 대기 후 다시 시도
}
