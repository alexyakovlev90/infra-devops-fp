#!/bin/bash

#kubectl apply -f postgres/postgres-configmap.yml
#kubectl apply -f postgres/postgres-deployment.yml
#kubectl apply -f postgres/postgres-service.yml


kubectl apply -f env-config.yml

kubectl apply -f trx-producer/
kubectl apply -f trx-processor/
kubectl apply -f trx-receiver/


