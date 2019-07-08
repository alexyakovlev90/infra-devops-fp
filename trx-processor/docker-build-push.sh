#!/bin/bash

docker build -t trx-processor .

docker tag trx-processor:latest alexyakovlev90/trx-processor:latest
docker push alexyakovlev90/trx-processor:latest
