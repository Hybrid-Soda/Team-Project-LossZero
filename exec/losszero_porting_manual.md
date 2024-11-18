# 📖 포팅 메뉴얼

## 목차

1. [사용 도구](#1-사용-도구)
2. [개발 도구](#2-개발-도구)
3. [개발 환경](#3-개발-환경)
4. [포트 정보](#4-포트-정보)
5. [구동 방법](#5-구동-방법)
---
## 1. 사용 도구

- 이슈 관리: JIRA
- IDE : Visual Studio, Intellij Ultimate
- 형상 관리: Gitlab
- 커뮤니케이션: Notion, Mattermost
- 디자인: Figma
- CI/CD: Jenkins

---
## 2. 개발 도구

### Frontend

- 프레임워크 : Vue3
- 라이브러리 : axios

### Backend

- 프레임워크 : Spring Boot
- 라이브러리 : JPA, Spring Security, JWT

### EM

- Raspberry Pi
- Arduino
---

## 3. 개발 환경

### Frontend

| name       | version |
|------------|---------|
| Node.js    | 20.15.0 |
| Vue3       | 18.3.1  |

### Backend

| name         | version |
|--------------|---------|
| Java         | 17      |
| Spring Boot  | 3.3.4   |

### Database

| name   | version |
|--------|---------|
| MySQL  | 9.0.1   |

### Infra

| name     | version                |
|----------|------------------------|
| Docker   | 27.3.1                 |
| Nginx    | nginx/1.18.0 (Ubuntu)  |
| Jenkins  | 2.482                  |
| Ubuntu   | 20.04.6 LTS            |

### EM

| name           | version      |
|----------------|--------------|
| Raspberry Pi   | 5            |
| python         | 3.9          |
| Arduino        | D1 R2 mini, UNO |

---
## 4. 포트 정보

### MySQL : 3306

### Backend : 8081

### FrontEnd : 5173

### MQTT broker

- MQTT : 1883
- WebSocket : 9001
---
## 5. 구동 방법

### FrontEnd

```bash
cd FE

npm install 

npm run dev
```

### BackEnd

- application.yml

```bash
spring:
  datasource:
    url: jdbc:mysql://k11e202.p.ssafy.io:3306/losszero?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul
    username: root
    password: losszero
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

  mqtt:
    clean-session: true
    connection-timeout: 100
  
  profiles:
    active: secret  # secret profile을 활성화

logging:
  level:
    org.springframework: DEBUG
```

### EM - 라즈베리파이

- 설치 라이브러리

```bash
**Package                                  Version
---------------------------------------- -----------

board                                    1.0
edge-tpu-silva                           1.0.5
google-pasta                             0.2.0
gpiozero                                 2.0.1
lgpio                                    0.2.2.0
matplotlib                               3.9.2
numpy                                    1.25.0
onnx                                     1.17.0
opencv-contrib-python                    4.10.0.84
opencv-python                            4.5.3.56
paho-mqtt                                2.1.0
pandas                                   2.2.3
Pi5Neo                                   1.0.5
pigpio                                   1.78
Pillow                                   9.5.0
pip                                      24.3.1
pycoral                                  2.0.0
pyserial                                 3.5
RPi.GPIO                                 0.7.1
rpi-lgpio                                0.6
rpi_ws281x                               5.0.0
scipy                                    1.13.1
spidev                                   3.6
tensorboard                              2.18.0
tensorboard-data-server                  0.7.2
tensorflow                               2.18.0
tensorflow-aarch64                       2.16.1
tensorflow-io-gcs-filesystem             0.37.1
tf_keras                                 2.18.0
tflite-runtime                           2.5.0.post1
torch                                    2.5.1
torchvision                              0.20.1
ultralytics                              8.3.27
ultralytics-thop                         2.0.10**
```

- 카메라 작동

```bash
conda activate yolo
cd Destop/main
python main.py
```