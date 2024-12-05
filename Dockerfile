FROM openjdk:23-jdk-slim AS build

RUN apt-get update && \
    apt-get install -y maven

COPY . .

RUN mvn clean install

FROM openjdk:23-jdk-slim

EXPOSE 8080

COPY --from=build /target/Biblioteca-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
