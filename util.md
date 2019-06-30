# Полезные команды



## Поднять ВМ с kafka в докере
```bash
export GOOGLE_PROJECT=otus-fp

docker-machine create --driver google \
    --google-machine-image https://www.googleapis.com/compute/v1/projects/ubuntu-os-cloud/global/images/family/ubuntu-1604-lts \
    --google-machine-type n1-standard-1 \
    --google-zone europe-west1-b \
    --google-open-port 9092/tcp \
    --google-open-port 2181/tcp \
    kafka
    
eval $(docker-machine env kafka)
```
