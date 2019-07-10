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

### trx-processor
- стартует на 9090 порту
- получает транзакции по пути `/api/process`
- обрабатывает транзакции, записывает в `DB_HOST` в таблицу **TRX_DATA_PROCESSED**
- swagger доступен `trx-processor:9090/swagger-ui.html`


## Как запустить проект

1) Ставим Kafka через docker-machine на удаленный хост
```bash
docker run -p 2181:2181 -p 9092:9092 \
    --env ADVERTISED_HOST=`docker-machine ip \`docker-machine active\`` \
    --env ADVERTISED_PORT=9092 \
    spotify/kafka
```

2) Задаем переменную окружения KAFKA_HOST=`docker-machine ip` 
в deploy манифестах 
- kubernetes/trx-producer/trx-producer-deployment.yml
- kubernetes/trx-receiver/trx-receiver-deployment.yml

3) Собираем артефакты (jar-файлы) с помощью Maven `mvn package` в директориях
- trx-receiver
- trx-processor
- trx-producer

4) Билдим докер образы и пушим в докер хаб
- запуск скрипта docker-build-push.sh

5) Запускаем скрипт kubernetes/deploy.sh


* Проверить работоспособность можно по логам trx-processor
