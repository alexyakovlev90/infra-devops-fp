#!/bin/bash

mvn clean package -Dmaven.test.skip=true

docker build -t demo .

docker tag demo:latest alexyakovlev90/demo:latest
docker push alexyakovlev90/demo:latest
