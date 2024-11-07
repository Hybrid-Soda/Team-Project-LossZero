#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

// 네트워크 정보 업데이트
const char* ssid = "losszero";
const char* password = "asd123123";
const char* mqtt_server = "k11e202.p.ssafy.io";

WiFiClient espClient;
PubSubClient client(espClient);

String arm_command = "";

void setup_wifi() {
  delay(10);
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

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void sendCommand(const char* command) {
  Serial.write(command);
}

void waitForCompletion() {
  while (true) {
    if (Serial.available()) {
      String message = Serial.readStringUntil('\n');
      message.trim();
      if (message.endsWith("Complete")) {
        break;
      }
    }
  }
}

void sendMqttMessage(const char* sender, const char* status) {
  StaticJsonDocument<200> doc;
  doc["sender"] = sender;
  doc["status"] = status;
  char messageBuffer[200];
  serializeJson(doc, messageBuffer);
  client.publish("realtime-cycle", messageBuffer);
  Serial.println("Sent Complete Message");
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");

  arm_command = "";
  for (int i = 0; i < length; i++) {
    arm_command += (char)payload[i];
  }

  // JSON 파싱
  StaticJsonDocument<200> doc;
  DeserializationError error = deserializeJson(doc, arm_command);
  if (error) {
    return;
  }
  
  const char* sender = doc["sender"];
  const char* status = doc["status"];
  Serial.println(sender);
  Serial.println(status);

  if (strcmp(sender, "camera") == 0) {
    if (strcmp(status, "defect") == 0) {
      sendCommand("Defect_Pos1");
      waitForCompletion();
      sendMqttMessage("arm", "complete");
    } else if (strcmp(status, "reusable") == 0) {
      sendCommand("Reusable_Pos1");
      waitForCompletion();
      sendMqttMessage("arm", "complete");
    }
  }
}

void reconnect() {
  while (!client.connected()) {
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    if (client.connect(clientId.c_str())) {
      client.subscribe("realtime-cycle");
    } else {
      delay(1000);
    }
  }
}

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
  Serial.begin(115200);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
  client.setKeepAlive(120);
}

void loop() {
  if (!client.connected()) {
    digitalWrite(LED_BUILTIN, HIGH);
    Serial.println("Reconnecting");
    reconnect();
    digitalWrite(LED_BUILTIN, LOW);
    Serial.println("Connected!");
  }
  client.loop();
}