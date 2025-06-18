FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/banking-system-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
