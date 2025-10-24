# syntax=docker/dockerfile:1
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

ARG JAR_FILE=target/psiRizerio-email-service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
