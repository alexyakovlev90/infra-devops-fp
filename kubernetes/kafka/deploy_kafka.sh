#!/usr/bin/env bash

kubectl create -f ./zookeeper-service.yml
kubectl create -f ./zookeeper-service-headless.yml
kubectl create -f ./zookeeper-statefulset.yml
#kubectl create -f ./zookeeper-disruptionbudget.yml

kubectl create -f ./kafka-service.yml
kubectl create -f ./kafka-service-headless.yml
kubectl create -f ./kafka-statefulset.yml

kubectl create -f ./pod-test.yml
