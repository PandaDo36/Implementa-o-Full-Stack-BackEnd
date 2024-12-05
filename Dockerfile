FROM ubuntu:lestest AS build

RUN apt-get update
RUN apt-get install openjdk-23-jdk -y
COPY . .
RUN apt-get install maven -y
RUN mvn clean install

From openjdk:23-jdk-slim

EXPOSE 8080

COPY --from=build /target/Biblioteca-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]
