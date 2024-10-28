# 1. Gradle 빌드 이미지 단계
FROM gradle:jdk17 AS build

# 작업 디렉터리 설정
WORKDIR /builder/

# Gradle 설정 파일 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle  # `gradle` 폴더 전체를 복사하여 필요한 Gradle 설정을 포함

# 의존성 캐싱을 위해 Gradle 빌드 실행
RUN gradle build --no-daemon || return 0  # 의존성 캐싱

# 소스 코드 복사
COPY src ./src

# bootJar 실행으로 JAR 파일 생성
RUN gradle bootJar

# 2. 최종 실행 이미지
FROM openjdk:17-slim

# 빌드 이미지에서 생성된 JAR 파일 복사
COPY --from=build /builder/build/libs/*.jar /app.jar

# JAR 파일 실행 설정
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 컨테이너 노출 포트
EXPOSE 8080
