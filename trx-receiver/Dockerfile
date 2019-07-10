FROM openjdk:8-jdk-alpine

ENV DB_HOST trx-processor-db-host
ENV DB_NAME mydb
ENV DB_USERNAME dbowner
ENV DB_PASSWORD s_cret

ENV TRX_PROCESSOR localhost:9090

ENV KAFKA_HOST localhost:9092
ENV TOPIC_NAME trx-topic

ARG JAR_FILE=target/trx-receiver.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
