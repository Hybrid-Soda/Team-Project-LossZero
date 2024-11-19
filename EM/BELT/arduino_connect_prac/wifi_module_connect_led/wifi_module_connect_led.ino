// LED가 연결된 핀 정의
#define ledPin = D5; // Wemos D1 R2 보드의 D1 핀은 GPIO 5입니다.

void setup() {
  // LED 핀을 출력 모드로 설정
  pinMode(ledPin, OUTPUT);
}

void loop() {
  // LED 켜기
  digitalWrite(ledPin, HIGH);
  delay(1000); // 1초 대기

  // LED 끄기
  digitalWrite(ledPin, LOW);
  delay(1000); // 1초 대기
}
