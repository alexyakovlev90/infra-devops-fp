FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/demo.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","-XX:+UseSerialGC","-Xss512k","-XX:MaxRAM=256m","/app.jar"]

