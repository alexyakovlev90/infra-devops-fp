#!/bin/bash

#kubectl apply -f kafka-micro/

kubectl apply -f postgres/postgres-configmap.yml
# kubectl create -f postgres/postgres-storage.yml
kubectl apply -f postgres/postgres-deployment.yml
kubectl apply -f postgres/postgres-service.yml

kubectl apply -f trx-processor/

#kubectl apply -f zookeeper/
#kubectl apply -f kafka/

kubectl apply -f trx-producer/
kubectl apply -f trx-receiver/
