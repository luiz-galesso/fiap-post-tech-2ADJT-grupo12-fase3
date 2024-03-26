FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY /target/api-restaurante-0.0.1-SNAPSHOT.jar api-restaurante.jar
CMD ["java", "-jar", "api-restaurante.jar"]