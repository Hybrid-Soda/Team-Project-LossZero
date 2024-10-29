#include "ElectromagnetControl.h"
#include <Arduino.h>

const int electromagnetPin = 3;

void initializeElectromagnet() {
  pinMode(electromagnetPin, OUTPUT);
  digitalWrite(electromagnetPin, LOW); // 초기 상태 OFF
}

void activateElectromagnet() {
  digitalWrite(electromagnetPin, HIGH);
  Serial.println("Electromagnet ON.");
}

void deactivateElectromagnet() {
  digitalWrite(electromagnetPin, LOW);
  Serial.println("Electromagnet OFF.");
}
