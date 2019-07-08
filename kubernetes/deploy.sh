#!/bin/bash

# kubectl create -f postgres/postgres-configmap.yml
# kubectl create -f postgres/postgres-storage.yml
kubectl apply -f postgres/postgres-deployment.yml
kubectl apply -f postgres/postgres-service.yml

kubectl apply -f trx-processor/trx-processor-deployment.yml
kubectl apply -f trx-processor/trx-processor-service.yml
