FROM openjdk:8-jdk-alpine

ENV KAFKA_HOST localhost:9092
ENV TOPIC_NAME trx-topic

ARG JAR_FILE=target/trx-producer.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","-XX:+UseSerialGC","-Xss512k","-XX:MaxRAM=256m","/app.jar"]

