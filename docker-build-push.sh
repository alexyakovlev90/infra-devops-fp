#!/bin/bash

docker build -t trx-processor trx-processor/
docker tag trx-processor:latest alexyakovlev90/trx-processor:latest
docker push alexyakovlev90/trx-processor:latest

docker build -t trx-producer trx-producer/
docker tag trx-producer:latest alexyakovlev90/trx-producer:latest
docker push alexyakovlev90/trx-producer:latest

docker build -t trx-receiver trx-receiver/
docker tag trx-receiver:latest alexyakovlev90/trx-receiver:latest
docker push alexyakovlev90/trx-receiver:latest

#docker build -t kafka-micro kafka/kafka/
#docker tag kafka-micro:latest alexyakovlev90/kafka-micro:latest
#docker push alexyakovlev90/kafka-micro:latest
