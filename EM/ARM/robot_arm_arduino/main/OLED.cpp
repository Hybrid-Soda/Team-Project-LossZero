// OLED.cpp

#include "OLED.h"
#include <Wire.h>
#include <SSD1306Ascii.h>
#include <SSD1306AsciiWire.h>

#define OLED_RESET 4
SSD1306AsciiWire display;

void initializeOLED() {
  Wire.begin();
  display.begin(&Adafruit128x64, 0x3C);
  display.setFont(Adafruit5x7);
  display.clear();
  display.println("Setup complete.");
}

void updateOLED(const char* state, bool electromagnetOn) {
  display.clear();

  // 상단 텍스트"
  display.setCursor(15, 0);
  display.println("Robot Arm Working!\n\n");

  // 현재 상태 표시
  display.println(String("State : ") + state + "\n");

  // 전자석 상태 표시
  display.println(String("Electromagnetic : ") + (electromagnetOn ? "On" : "Off") + "\n");
}
