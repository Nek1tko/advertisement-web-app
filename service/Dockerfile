# Build stage
FROM maven:3.6.0-jdk-11-slim as builder

COPY . /service
WORKDIR /service

RUN mvn clean package

# Packege stage
FROM openjdk:11-jre-slim

WORKDIR /service
COPY --from=builder /service/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
