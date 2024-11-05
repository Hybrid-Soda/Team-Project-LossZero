// ServoControl.h

#ifndef SERVO_CONTROL_H
#define SERVO_CONTROL_H

#include <Servo.h>

// 모드 정의
enum Mode {
  CONVEYOR1,
  CONVEYOR2,
  STANDBY,
  RECYCLE,
  DEFECTIVE,
  WAYPOINT
};

// 서보 모터 초기화 함수
void initializeServos();

// 서보 모터 부드럽게 이동 함수
void moveServoSmoothly(Servo &servo, float &currentAngle, float targetAngle, const char* axisName);

// 특정 모드로 이동하는 함수
void setModeAngles(Mode mode);

// 대기 모드로 이동하는 함수
void moveToStandby();

// 시퀀스 실행 함수
void executeSequence(int sequenceType,  int conveyorPosition);

#endif
