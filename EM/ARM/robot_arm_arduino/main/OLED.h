// OLED.h

#ifndef OLED_H
#define OLED_H

#include <Arduino.h>

void initializeOLED();
void updateOLED(const char* state, bool electromagnetOn);

#endif
