#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <SoftwareSerial.h>
#include <ArduinoJson.h>

SoftwareSerial uno(D3, D4);

// 네트워크 및 MQTT 서버 설정

const char* ssid = "myGalaxy";
const char* password = "asd156156";
const char* mqtt_server = "k11e202.p.ssafy.io";

// 변수 초기화

WiFiClient espClient;
PubSubClient client(espClient);

#define MSG_BUFFER_SIZE    (50)
char msg[MSG_BUFFER_SIZE];
#define sensor D7


StaticJsonDocument<200> doc;
// 센서 상태
int sensor_flag = 0; 
// 전원 on,off
int on_off_flag = 0; 
// 0 : off
// 1 : on
// normal 인가 아닌가
int isNormal = 0;

// wifi 연결 설정
void setup_wifi() {

  delay(10);
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  // WiFi.setSleep(false);
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  String message; 

  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }

  DeserializationError error = deserializeJson(doc, message);
  
  if (error) {
    Serial.print(F("deserializeJson() 실패: "));
    Serial.println(error.f_str());
    return;
  }

  // JSON 데이터에서 각 값을 추출합니다.
  const char* sender = doc["sender"];
  String sender_str = String(sender);

  if (sender_str == "web") {
    // 웹에서 메세지를 보낸다
    const char* message = doc["message"];
    Serial.println("message: " + String(message));

    if (String(message) == "on") {
      Serial.println("가동해야함!");
      uno.write("1");
      on_off_flag = 1;

    } else if (String(message) == "off") {
      Serial.println("멈춰야함!");
      uno.write("0");
      on_off_flag = 0;   
    }

  } else if (sender_str == "camera") {
    const char* status = doc["status"];
    if (String(status) == "reusable" || String(status) == "defective") {
      Serial.println("멈춰야함!");
      uno.write("0");
      on_off_flag = 0;
    } else if (String(status) == "normal") {
      Serial.println("가동해야함!");
      on_off_flag = 1;
      sensor_flag = 0;
      isNormal = 1;
    }
  } else if (sender_str == "arm") {
    const char* status = doc["status"];
    if (String(status) == "belt_on") {
      Serial.println("가동해야함!");
      uno.write("1");
      on_off_flag = 1;
      sensor_flag = 0;
    } 
  } else if (sender_str == "belt") {
    const char* status = doc["status"];
    if (String(status) == "off") {
      Serial.println("멈춰야함!");
      uno.write("0");
      on_off_flag = 0;
    } else if (String(status) == "on") {
      Serial.println("가동해야함!");
      uno.write("1");
      on_off_flag = 1;
      sensor_flag = 0;
    } 
  }

  // 추출한 값을 출력합니다.
  Serial.println("Sender: " + String(sender));
  Serial.println();
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");

      client.subscribe("realtime-oper");
      client.subscribe("realtime-cycle");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {
  pinMode(sensor, INPUT);
  Serial.begin(115200);
  uno.begin(115200);
  setup_wifi();
  client.setClient(espClient);
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  // keep alive 120초로 설정
  client.setKeepAlive(120);
}

void loop() {

  if (!client.connected()) {
    Serial.println("연결 안됨");
    reconnect();
  }
  
  client.loop();

  if (on_off_flag && digitalRead(sensor) == 0 && sensor_flag == 0) {
    Serial.println("센서 감지");
    client.publish("realtime-cycle", "{ \"sender\": \"belt\", \"status\": \"off\" }");
    sensor_flag = 1;
  }

  if (isNormal == 1) {
    uno.write("1");
    delay(700);
    client.publish("realtime-cycle", "{ \"sender\": \"belt\", \"status\": \"on\" }");
    isNormal = 0; // 신호 한번만 전송 후 리셋
  }
  
} 