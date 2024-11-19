#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "E102hs";                // WiFi SSID
const char* password = "ssafy123";           // WiFi 비밀번호
const char* serverUrl = "http://192.168.30.128:8000/wifi/command";  // FastAPI 서버 명령 URL

WiFiClient wifiClient;

void setup() {
  Serial.begin(115200);
  pinMode(LED_BUILTIN, OUTPUT); // 내장 LED 사용. 실제 사용할 핀으로 교체 가능

  // WiFi 연결 설정
  WiFi.begin(ssid, password);
  Serial.println("Connecting to WiFi...");

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("Connected to WiFi");
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin(wifiClient, serverUrl);

    // 서버에 GET 요청
    int httpResponseCode = http.GET();

    // 응답 처리
    if (httpResponseCode == HTTP_CODE_OK) {  // HTTP 상태 코드 200 확인
      String payload = http.getString();
      Serial.println("Response: " + payload);

      // "on" 명령 확인
      if (payload.indexOf("\"command\":\"on\"") != -1) {
        Serial.println("Turning ON LED");
        digitalWrite(LED_BUILTIN, LOW); // LED 켜기 (LED_BUILTIN은 LOW가 켜짐)

      // "off" 명령 확인
      } else if (payload.indexOf("\"command\":\"off\"") != -1) {
        Serial.println("Turning OFF LED");
        digitalWrite(LED_BUILTIN, HIGH); // LED 끄기

      } else {
        Serial.println("Unknown command received");
      }

    } else {
      Serial.print("Error in GET request, code: ");
      Serial.println(httpResponseCode);
    }

    http.end();  // 요청 종료
  } else {
    Serial.println("WiFi not connected");
  }

  delay(3000);  // 5초 간격으로 서버에 명령 요청
}
