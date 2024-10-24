#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "ggg";           // WiFi SSID
const char* password = "qwer1234";   // WiFi 비밀번호
const char* serverUrl = "http://192.168.30.158:8000/wifi/data"; // FastAPI 서버 주소 (서버의 IP 주소 사용)

WiFiClient wifiClient;

void setup() {
  Serial.begin(115200);

  // WiFi 모드를 스테이션 모드로 설정
  WiFi.mode(WIFI_STA);
  
  WiFi.begin(ssid, password);
  Serial.println("setup");

  // WiFi 연결 시도
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;

    // 서버와 연결
    http.begin(wifiClient, serverUrl);
    http.addHeader("Content-Type", "application/json");

    // 아두이노 데이터 예시
    String jsonData = "{\"temperature\": 24, \"humidity\": 10}";

    // HTTP POST 요청
    int httpResponseCode = http.POST(jsonData);

    // 응답 코드 확인
    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.println(httpResponseCode);
      Serial.println(response);

      // 온도와 습도 값 인덱싱으로 추출
      int tempIndex = response.indexOf("temperature");
      int humidIndex = response.indexOf("humidity");
      
      // 온도 값 추출
      int startTemp = response.indexOf(":", tempIndex) + 1;
      int endTemp = response.indexOf(",", startTemp);
      String temperature = response.substring(startTemp, endTemp);
      
      // 습도 값 추출
      int startHumid = response.indexOf(":", humidIndex) + 1;
      int endHumid = response.indexOf("}", startHumid);
      String humidity = response.substring(startHumid, endHumid);

      // 로그 출력
      Serial.print("현재 온도: ");
      Serial.print(temperature);
      Serial.println("도");
      Serial.print("현재 습도: ");
      Serial.print(humidity);
      Serial.println("%");

    } else {
      Serial.print("Error on sending POST: ");
      Serial.println(httpResponseCode);
    }

    http.end();
  }

  delay(5000); // 5초 간격으로 전송
}
