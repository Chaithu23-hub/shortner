FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/shortner-0.0.1-SNAPSHOT.jar shortner.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","shortner.jar"]