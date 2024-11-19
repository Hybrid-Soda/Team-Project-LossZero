#include <Adafruit_NeoPixel.h>

// 네오픽셀 설정
#define PIN 6         // 네오픽셀 데이터 핀에 연결된 아두이노 핀 번호
#define NUMPIXELS 12  // 원형 패널의 LED 수 (예: 12개)

// 네오픽셀 객체 생성
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

void setup() {
  strip.begin();          // 네오픽셀 시작
  strip.setBrightness(5); // 밝기 설정 (필요에 따라 조절 가능)
  strip.show();           // 모든 LED를 초기화 상태로 유지
}

void loop() {
  progressEffectWithGreenToBlueGradient(100); // 녹색에서 파란색으로 진행 효과

  // 특정 조건에서 경고등 상태를 표시하는 코드 예시
  unsigned long time = millis() % 15000;
  if (time < 5000) {
    warningYellowEffect(200); // 첫 2초 동안 노란색 경고등 효과
  } else if (time < 10000) {
    warningRedEffect(200); // 그 다음 2초 동안 빨간색 경고등 효과
  }
}

// 녹색에서 파란색으로 그라데이션이 있는 프로그레스 효과
void progressEffectWithGreenToBlueGradient(uint16_t interval) {
  for (int i = 0; i < strip.numPixels(); i++) {
    int greenValue = map(i, 0, strip.numPixels() - 1, 255, 0); // 녹색에서 감소
    int blueValue = map(i, 0, strip.numPixels() - 1, 0, 255);  // 파란색으로 증가
    strip.setPixelColor(i, strip.Color(0, greenValue, blueValue)); // 녹색->파란색 그라데이션
    strip.show();
    delay(interval); // 각 LED가 켜지는 간격
  }
  // 모든 LED를 꺼서 초기화
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, strip.Color(0, 0, 0));
  }
  strip.show();
}

// 노란색 경고등 효과
void warningYellowEffect(uint16_t interval) {
  for(int iter = 0; iter<2; iter++) {
    for (int i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(255, 255, 0)); // 노란색
    }
    strip.show();
    delay(interval);

    for (int i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(0, 0, 0)); // 꺼짐
    }
    strip.show();
    delay(interval);
  }
}

// 빨간색 경고등 효과
void warningRedEffect(uint16_t interval) {
  for(int iter = 0; iter<2; iter++) {
    for (int i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(255, 0, 0)); // 빨간색
    }
    strip.show();
    delay(interval);

    for (int i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(0, 0, 0)); // 꺼짐
    }
    strip.show();
    delay(interval);
  }
}
