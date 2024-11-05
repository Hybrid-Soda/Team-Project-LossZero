int Electromagnet = 2;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(Electromagnet, OUTPUT); 
}

void loop() {
  // put your main code here, to run repeatedly:

  if(Serial.available()){
    char c = Serial.read();
    if (c=='0'){
      int Electromagnet = 2;
      Serial.println("전자석 on");
      digitalWrite(Electromagnet, HIGH);
    }
    else{
      Serial.println("전자석 off");
      digitalWrite(Electromagnet, LOW);
    }
  }

  

  delay(1000);
}
