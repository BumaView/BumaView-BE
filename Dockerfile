# ------------------------------
# 1. 빌드 단계 (Java 21 JDK)
# ------------------------------
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# 프로젝트 소스 복사
COPY . .

# Gradle Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle 빌드 (테스트 스킵)
RUN ./gradlew build -x test

# ------------------------------
# 2. 실행 단계 (경량화)
# ------------------------------
FROM openjdk:21-jdk-slim

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
