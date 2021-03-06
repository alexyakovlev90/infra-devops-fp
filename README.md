# Infrastructure DevOps

## Описание проекта

Есть условный платежный шлюз, через который идет поток транзакций.
Этот поток транзакций попадает в очередь, которую слушает сервис.
Слушатель очереди сохраняет все транзакции в БД, а на некоторые транзакции обрабатывает,
начисляет комиссию, баллы лояльности и т.д.

Проект состоит из 3х сервисов и визуально выглядит следующим образом:
![title](app_scheme.png)

В качестве мока платежного шлюза на схеме изображен сервис `trx-producer`,
который будет генерировать транзакции и складывать в очередь. Для увеличения 
нагрузки можно поднять несколько инстанцов сервиса. 

Прослушкой очереди занимается сервис `trx-receiver`. Его роль:
1) записать все пришедшие транзакции в БД
2) отправить часть транзакций на обработку в сервис `trx-processor`

Сервис `trx-processor` представляет собой простой REST сервис, 
содержащий контроллер с методами получения транзакций и бизнес логику обработки 
полученных транзакций. Обработанные транзакции сервис так же сохраняет в БД.


### trx-producer
- консольное приложение
- отправляет транзакции в Kafka по адресу `KAFKA_HOST` в топик `TOPIC_NAME`
- `KAFKA_HOST` по умолчанию _localhost:9092_
- `TOPIC_NAME` по умолчанию _sample-topic_

### trx-receiver
- стартует на 8080 порту
- слушает Kafka по адресу `KAFKA_HOST` из топика `TOPIC_NAME`
- пишет транзакции в Postgres `DB_HOST` в таблицу **TRX_DATA**
- отправляет транзакции в _trx-processor_
- `KAFKA_HOST` по умолчанию _localhost:9092_
- `TOPIC_NAME` по умолчанию _sample-topic_
- swagger доступен `trx-receiver:8080/swagger-ui.html`

### trx-processor
- стартует на 9090 порту
- получает транзакции по пути `/api/process`
- обрабатывает транзакции, записывает в `DB_HOST` в таблицу **TRX_DATA_PROCESSED**
- swagger доступен `trx-processor:9090/swagger-ui.html`


## Как запустить проект
### old
1) Предполагается, что для проекта подняты Kafka и Postgres.
Сейчас Kafka и Postgres установлены с помощью Google Click to Deploy

3) Собираем артефакты (jar-файлы), билдим докер образы и пушим в докер хаб
```bash
bash docker-build-push.sh
```

4) Запуск приложения
```bash
# локально
bash docker-run.sh
# minikube
minikube start
bash kubernetes/deploy.sh
```

* Проверить работоспособность можно 
```
trx-processor:9090/swagger-ui.html
trx-receiver:8080/swagger-ui.html
```

## Demo
```shell script
cd ~/.kube/

kubectl config get-contexts
kubectl config use-context CONTEXT_NAME
kubectl apply -f k8s/
kubectl get pods

kubectl port-forward . 8081:8080

kubectl describe pod 
kubectl logs 

minikube dashboard {--v=7}
minikube service kubernetes-dashboard -n kube-system
```