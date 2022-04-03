# Build stage
FROM maven:3.6.0-jdk-11-slim as builder

COPY . /service
WORKDIR /service

RUN mvn clean package -Dmaven.test.skip

# Packege stage
FROM openjdk:11-jre-slim

WORKDIR /service
COPY --from=builder /service/target/*.jar app.jar

RUN mkdir /images
COPY ./src/main/resources/images /images

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
