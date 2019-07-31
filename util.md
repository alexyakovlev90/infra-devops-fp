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

docker-machine create --driver google \
    --google-machine-image https://www.googleapis.com/compute/v1/projects/ubuntu-os-cloud/global/images/family/ubuntu-1604-lts \
    --google-machine-type n1-standard-1 \
    --google-zone europe-west1-b \
    --google-open-port 5432/tcp \
    --google-open-port 5050/tcp \
    --google-open-port 9090/tcp \
    postgres
eval $(docker-machine env postgres)  
eval $(docker-machine env --unset)  
```

### Kafka-full
- установлен zookeeper-micro https://github.com/kow3ns/kubernetes-zookeeper/tree/master/manifests
- kafka https://github.com/kow3ns/kubernetes-kafka/tree/master/manifests

### Kafka-micro

- Установка и проверка kafka
```bash
docker run -p 2181:2181 -p 9092:9092 \
    --env ADVERTISED_HOST=`docker-machine ip \`docker-machine active\`` \
    --env ADVERTISED_PORT=9092 \
    spotify/kafka

bash kafka-console-consumer --bootstrap-server 34.77.246.22:9092 --topic test
bash kafka-console-producer --broker-list 34.77.246.22:9092 --topic test
```


- Postgres proxy
https://cloud.google.com/sql/docs/postgres/connect-admin-proxy

- docker 
```bash
docker run --name pg -p 5432:5432 \
    -v /root/pgdata:/var/lib/postgresql/data:Z \
    -e POSTGRES_DB=mydb -e POSTGRES_USER=dbowner \
    -e POSTGRES_PASSWORD=s_cret \
    -d postgres:alpine
```


- Загрузим наш образ на docker hub
```bash
docker build -t trx-processor .

docker tag trx-processor:latest alexyakovlev90/trx-processor:1.0
docker push alexyakovlev90/trx-processor:1.0

docker run -p 9090:9090 \
    -d alexyakovlev90/trx-processor:1.0
```

- пробрасывание сетевых портов POD на локальную машину
```bash
kubectl get pods --selector component=trx-processor
kubectl port-forward <pod-name> 8080:9090
```
- смотреть логи
```bash
kubectl logs <pod-id>
```
- проверка сервисов и деплойментов в minikube
```bash
minikube service ui
minikube services list 

minikube dashboard
minikube service kubernetes-dashboard -n kube-system

```

https://severalnines.com/blog/using-kubernetes-deploy-postgresql


## Установка Кафки в gcloud

- Завести Kafka Google Click to Deploy

- Прописать конфиг в /opt/kafka/config/server.properties

If Internal IP is 10.168.4.9 and port is 9092 and External IP is 35.196.212.10 and port is 3101 then your propert will look like ,
listeners=PLAINTEXT://10.168.4.9:9092 &
advertised.listeners = PLAINTEXT://35.196.212.10:3101

- перезапуск кафка
```bash
sudo systemctl restart kafka
```

- проверка работы
```bash
kafka-console-consumer.sh --bootstrap-server 10.156.0.2:9092 --topic sample-topic --from-beginning
kafka-console-producer.sh --broker-list 35.246.191.39:9092 --topic sample-topic
./kafka-console-producer --broker-list 35.246.191.39:9092 --topic sample-topic
```

TODO переписать на ансибл роль / терраформ


## Установка Postgres
