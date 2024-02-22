FROM openjdk:17-jdk-slim
WORKDIR application
ARG JAR_FILE=build/libs/notepad-service.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","application.jar"]