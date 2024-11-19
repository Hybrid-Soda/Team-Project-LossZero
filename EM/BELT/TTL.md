# TTL

## 10/24

### 파일 구조도

- ardu_test : FastAPI(백엔드 테스트 서버)
- arduino_connect : 아두이노 WIFI D1 R2 보드(ESP8266) http 송신 연결을 위한 폴더

### 아두이노 http 송신 과정

- https://eduino.kr/product/detail.html?product_no=434&nbsrc=adwords_x&nbkw=&gad_source=1&gclid=Cj0KCQjwveK4BhD4ARIsAKy6pMJML6Jhk1Vu_mi64lW89FGFeEggAgb6OxVrbx7K1bjwIwOtrRNF0aoaAiduEALw_wcB

- 아두이노 IDE 기본 세팅
- FastAPI 서버 실행
  - uvicorn main:app --host 0.0.0.0 --port 8000
- 아두이노 코드에서 wifi 연결
  - wifi 이름, 비밀번호에 특수 문자 있으면 안된다.
- 연결 url

  - 현재는 local에서 서버를 돌린다.
  - url : cmd -> ipconfig > IPv4 주소 에 해당하는 주소 + 포트번호

- 방화벽 설정 확인

  1. Windows 방화벽 설정 열기:

  - 시작 메뉴에서 "Windows 보안"을 검색하고 열기.
  - "방화벽 및 네트워크 보호" 선택.

  2. 고급 설정 클릭하여 고급 보안 Windows 방화벽을 열기.
  3. 인바운드 규칙에서 새로운 규칙 추가:

  - "새 규칙" 클릭.
  - "포트" 선택하고 다음 클릭.
  - "TCP"와 "특정 로컬 포트"에서 8000 입력 후 다음 클릭.
  - "연결 허용" 선택 후 다음 클릭.
  - 필요한 프로필(도메인, 개인, 공용)을 선택하고 다음 클릭.
  - 규칙 이름을 지정하고 마침 클릭.

  4. 아두이노 연결, 코드 upload
  5. Serial Monitor 에서 확인
