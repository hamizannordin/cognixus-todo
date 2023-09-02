FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp
COPY target/*.jar app.jar
COPY application.yml application.yml

ENTRYPOINT ["java","-jar","/app.jar"]