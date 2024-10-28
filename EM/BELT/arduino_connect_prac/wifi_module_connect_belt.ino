#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <AccelStepper.h>

const char* ssid = "E102hs";            // WiFi SSID
const char* password = "ssafy123";      // WiFi 비밀번호
const char* serverUrl = "http://192.168.30.128:8000/wifi/data"; // FastAPI 서버 주소

#define enablePin 12
#define dirxPin 14
#define stepxPin 5
#define motorInterfaceType 1
#define cmdxPin 13
#define sensorx 9

AccelStepper stepperx = AccelStepper(motorInterfaceType, stepxPin, dirxPin);
WiFiClient wifiClient;

unsigned long lastToggleTime = 0;  // 마지막 상태 전환 시간
bool moving = false;               // 모터의 현재 상태 (움직임 / 정지)
bool lastSentState = false;        // 서버에 마지막으로 전송된 상태

void setup() {  
  Serial.begin(115200);

  // WiFi 연결 설정
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.println("Connecting to WiFi...");
  
  // WiFi 연결 시도
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");

  // 핀 모드 설정
  pinMode(enablePin, OUTPUT);
  pinMode(sensorx, INPUT_PULLUP);
  pinMode(cmdxPin, OUTPUT);
  digitalWrite(enablePin, LOW);
  digitalWrite(cmdxPin, HIGH);

  // 모터 속도 설정
  stepperx.setMaxSpeed(1000);
  stepperx.setSpeed(400);
}

void loop() {
  // 2초마다 모터 상태 전환
  if (millis() - lastToggleTime >= 5000) {
    moving = !moving;  // 상태 전환
    lastToggleTime = millis();  // 마지막 전환 시간 업데이트

    if (moving) {
      Serial.println("Conveyor running");
      digitalWrite(cmdxPin, HIGH);  // 모터 방향 설정
      stepperx.setSpeed(400);       // 모터 속도 설정
      stepperx.runSpeed();          // 모터 시작
    } else {
      Serial.println("Conveyor stopped");
      stepperx.stop();  // 모터 정지
    }

    // 상태가 변경될 때마다 서버로 전송
    sendStateToServer();
  }

  // 모터가 움직이는 동안 계속 runSpeed() 호출
  if (moving) {
    stepperx.runSpeed();
  }

  // // 5초마다 서버에 상태를 동기화 (추가적인 상태 확인)
  // if (millis() - lastToggleTime >= 5000) {
  //   sendStateToServer();
  // }
}

void sendStateToServer() {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin(wifiClient, serverUrl);
    http.addHeader("Content-Type", "application/json");

    // JSON 데이터 생성 (현재 컨베이어 상태와 임의의 온도 값)
    String conveyorState = moving ? "running" : "stopped";
    float temperature = 25.0;  // 온도 값 예시
    String jsonData = "{\"conveyor_state\": \"" + conveyorState + "\", \"temperature\": " + String(temperature) + "}";
    
    // HTTP POST 요청
    int httpResponseCode = http.POST(jsonData);
    
    // 응답 코드 및 내용 출력
    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.println("Response from server: " + response);
    } else {
      Serial.print("Error on sending POST: ");
      Serial.println(httpResponseCode);
    }

    http.end();
  }
}
