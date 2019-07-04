FROM openjdk:8-jdk-alpine

ENV DB_HOST 34.77.149.89
ENV DB_NAME mydb
ENV DB_USERNAME dbowner
ENV DB_PASSWORD s_cret

ARG JAR_FILE=target/trx-processor.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
