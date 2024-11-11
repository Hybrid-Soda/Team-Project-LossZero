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
  stepperx.setSpeed(-500);
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
    } 
    
  }

  if (flag) {
    stepperx.setSpeed(-500);
    stepperx.runSpeed();
    digitalWrite(enablePin, LOW);
  } else {
    stepperx.stop();
    digitalWrite(enablePin, HIGH);
  }
}