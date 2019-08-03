#!/bin/bash

docker run --name trx-producer \
    -d --rm \
    --env-file=env_var.txt \
    alexyakovlev90/trx-producer:latest

docker run --name trx-processor \
    -d --rm -p 9090:9090 \
    --env-file=env_var.txt \
    alexyakovlev90/trx-processor:latest

docker run --name trx-receiver \
    -d --rm -p 8080:8080 \
    --network="host" \
    --env-file=env_var.txt \
    alexyakovlev90/trx-receiver:latest
