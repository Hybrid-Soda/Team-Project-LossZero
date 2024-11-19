#include <SoftwareSerial.h>

SoftwareSerial uno(D1,D2);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  uno.begin(9600);

}

void loop() {
  // put your main code here, to run repeatedly:

  
  uno.write('0');
  

  while(true){
    if(uno.available()) break;
  }

  String data = uno.readStringUntil(0x0a);
  Serial.println(data);

  delay(3000);

  uno.write('1');

  while(true){
    if(uno.available()) break;
  }

  
  Serial.println(data);

  delay(3000);

}
