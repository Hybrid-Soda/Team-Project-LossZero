// Arduino B Code

#include <SoftwareSerial.h>
#include "ServoControl.h"
#include "ElectromagnetControl.h"
#include "OLED.h"

SoftwareSerial mySerial(10, 11); // RX, TX

void setup() {
  Serial.begin(9600);
  mySerial.begin(9600);
  initializeServos();
  initializeElectromagnet();
  initializeOLED();
  moveToStandby();
  Serial.println("Arduino B ready.");
  sendMessage("Arduino B ready.");
}

void loop() {
  if (mySerial.available()) {
    String command = mySerial.readStringUntil('\n');
    command.trim();
    Serial.println("Received: " + command);

    if (command == "Command1") {
      executeSequence(1, 1);
      sendMessage("Command1 Complete");
    } else if (command == "Command2") {
      executeSequence(1, 2);
      sendMessage("Command2 Complete");
    } else if (command == "Command3") {
      executeSequence(2, 1);
      sendMessage("Command3 Complete");
    } else if (command == "Command4") {
      executeSequence(2, 2);
      sendMessage("Command4 Complete");
    } else {
      Serial.println("Unknown command received.");
    }
  }
}

void sendMessage(String message) {
  mySerial.println(message);
  Serial.println("Sent: " + message);
}
