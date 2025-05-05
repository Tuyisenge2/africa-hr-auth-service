FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/Authentication-service-0.0.1-SNAPSHOT.jar Authentication-service.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "Authentication-service.jar"]
