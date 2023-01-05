FROM openjdk:11.0.15-slim-buster
LABEL Description="Image API Price" Vendor="ACME Products" Version="1.0.0"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app-api.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","/app-api.jar"]