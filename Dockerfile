# 1. Java 17을 제공하는 공식 OpenJDK 이미지 사용
FROM eclipse-temurin:17-jdk-alpine

# 2. JAR 파일을 컨테이너 내부에 복사 (build/libs 경로는 프로젝트 상황에 맞게 수정)
ARG JAR_FILE=build/libs/weatherpick-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 3. 포트 개방 (스프링부트 기본 포트)
EXPOSE 8080

# 4. 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
