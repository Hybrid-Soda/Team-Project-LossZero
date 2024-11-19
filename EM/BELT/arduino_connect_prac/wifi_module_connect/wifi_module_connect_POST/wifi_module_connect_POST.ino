#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

const char* ssid = "E102hs";
const char* password = "ssafy123";

ESP8266WebServer server(80);  // HTTP 서버를 80번 포트에서 실행

String command = "off";  // 기본 상태

void handleRoot() {
  server.send(200, "text/plain", "Arduino is running");
}

void handleCommand() {
  if (server.hasArg("new_command")) {  // "new_command" 파라미터 확인
    command = server.arg("new_command");  // 명령 업데이트
    server.send(200, "application/json", "{\"status\": \"success\", \"command\": \"" + command + "\"}");  // JSON 형식으로 응답
    Serial.println("Command set to: " + command);

    if (command == "on") {
      digitalWrite(LED_BUILTIN, LOW);  // LED 켜기
    } else {
      digitalWrite(LED_BUILTIN, HIGH);  // LED 끄기
    }
  } else {
    server.send(400, "application/json", "{\"status\": \"error\", \"message\": \"Missing new_command parameter\"}");
  }
}

void setup() {
  Serial.begin(115200);
  pinMode(LED_BUILTIN, OUTPUT);
  
  WiFi.begin(ssid, password);
  Serial.println("Connecting to WiFi...");

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("Connected to WiFi. IP address: ");
  Serial.println(WiFi.localIP());

  server.on("/", handleRoot);
  server.on("/update_command", HTTP_POST, handleCommand);  // /update_command 엔드포인트 설정

  server.begin();
  Serial.println("HTTP server started");
}

void loop() {
  server.handleClient();  // 클라이언트 요청 처리
}
