#include <AccelStepper.h>

#define enablePin 8
#define dirxPin 2
#define stepxPin 5
#define motorInterfaceType 1

AccelStepper stepperx = AccelStepper(motorInterfaceType, stepxPin, dirxPin);

String Serial_Data = "";
char c;
int flag = 0;

void setup() {
  pinMode(enablePin, OUTPUT);
  Serial.begin(115200);
  Serial.flush();
  digitalWrite(enablePin, LOW);
  stepperx.setMaxSpeed(1000);
  stepperx.setSpeed(-800);
}

void loop() {
  if(Serial.available()) {
    c = Serial.read();
    if(c == '1') {
      Serial.println("ON");
      flag = 1;
      digitalWrite(enablePin, LOW);
    } else if (c == '0') {
      Serial.println("OFF");
      flag = 0;
      digitalWrite(enablePin, HIGH);
    } else if (c == '2') {
      digitalWrite(enablePin, LOW);
      stepperx.setSpeed(-800);
      stepperx.runSpeed();

      // 특정 거리만큼 이동하도록 설정
      // int distance_steps = 10; // 이동할 스텝 수 (예: 1000 스텝)
      // stepperx.moveTo(stepperx.currentPosition() + distance_steps);
      // stepperx.runToPosition(); // 해당 위치로 이동 후 멈춤

    }
  }

  if (flag) {
    stepperx.setSpeed(-800);
    stepperx.runSpeed();
    digitalWrite(enablePin, LOW);
  } else {
    stepperx.stop();
    digitalWrite(enablePin, HIGH);
  }
}
