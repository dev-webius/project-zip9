FROM openjdk:17-ea-11-jdk-slim AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM openjdk:17-ea-11-jdk-slim
COPY --from=builder build/libs/*.jar Zip9ApiApplication.jar
EXPOSE 8080
CMD java -jar /Zip9ApiApplication.jar