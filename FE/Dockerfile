# Node.js 이미지 사용
FROM node:20

# 작업 디렉토리 설정
WORKDIR /app

# package.json과 package-lock.json 파일 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 애플리케이션 코드 복사
COPY . .

# 개발 서버 실행
CMD ["npm", "run", "dev"]
