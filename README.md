# upr-cms-service

Сервис управляет статическим контентом витрины

## Требования к среде выполнения

JDK 21+

## Требование к БД

Сервис работает с БД Postgres 12+
Необходимо заранее создать БД и установить расширения:

- pg_trgm 
- postgis

## Миграция схемы БД

Скрипты миграций расположены в  директории flyway/migrations
Для миграции используется команда утилиты flyway:

```flyway -url=jdbc:postgresql://localhost:5432/cms -user=cms -password=cms -connectRetries=3 migrate```

## Конфигурационные параметры приложения

### Способы задания конфигурационных параметров

- разместить application.yml файл рядом с приложением, переопределить параметры по-умолчанию в нем 
- через переменные окружения с ключами соответствующими параметрам, например spring.datasource.url 
переопределяется через SPRING_DATASOURCE_URL переменную окружения

### Конфигурация порта

| Параметр    | Описание                                                       |
|-------------|----------------------------------------------------------------|
| server.port | tcp-порт на котором запускается приложение (по-умолчанию 8080) |


### Конфигурация БД

| Параметр                   | Описание                                                                     |
|----------------------------|------------------------------------------------------------------------------|
| spring.datasource.url      | jdbc-строка подключения к БД (например jdbc:postgresql://localhost:5432/cms) |
| spring.datasource.username | пользователь БД                                                              |
| spring.datasource.password | пароль пользователя БД                                                       |


### Конфигурация сжатия фото и видео
| Параметр                                         | Описание                                                                       | Пример              |
|--------------------------------------------------|--------------------------------------------------------------------------------|---------------------|
| app.upload.compression.compressionEnabled        | Флаг, включающий/отключающий сжатие для всех входящих файлов                   | true                |
| app.upload.compression.video.compress-min-length | Минимальный размер видео (в КБ), после которого выполняется сжатие             | 51200               |
| app.upload.compression.video.captureWidth        | Ширина                                                                         | 1920                |
| app.upload.compression.video.captureHeight       | Высота                                                                         | 1080                |
| app.upload.compression.video.tune                | Настройка, отвечающая за оптимизацию кодирования                               | zerolatency         |
| app.upload.compression.video.video-bitrate       | Битрейт видео,бит/c                                                            | 2000000 = 2000 кб/c |
| app.upload.compression.video.quality             | Качество видео после сжатия, от 0 (лучшее) до 52 (худшее)                      | 30                  |
| app.upload.compression.video.preset              | Глубина сжатия, сильно влияет на общее время сжатия                            | medium              |
| app.upload.compression.video.target-format       | Выходной формат видео после сжатия                                             | mp4                 |
| app.upload.compression.video.preserved-format    | Формат входного видео, который не изменяется после сжатия                      | webm                |
| app.upload.compression.video.audio-quality       | Качество аудио после сжатия, от 0 (лучшее) до 52 (худшее)                      | 0                   |
| app.upload.compression.video.audio-bitrate       | Битрейт видео, бит/c                                                           | 192000 = 192 кб/c   |
| app.upload.compression.video.audio-sample-rate   | Частота дискретизации аудио, Гц                                                | 44100 = 44 кГц      |
| app.upload.compression.video.audio-channels      | Количество аудио каналов                                                       | 2                   |
| app.upload.compression.image.compress-min-length | Минимальный размер изображения (в КБ), после которого выполняется сжатие       | 200                 |
| app.upload.compression.image.quality             | Коэффициент качества изображения после сжатия, от 0.0 (худшее) до 1.0 (лучшее) | 0.3                 |
| app.upload.compression.image.scale               | Коэффициент масштабирования изображения при сжатии                             | 1.0                 |


### Конфигурация по-умолчанию
[application.yml](src/main/resources/application.yml)

## Развертывание на локальном окружении ##
1. `mvn package`
2. `docker-compose up`

или

1. `docker run --name=cms-pg -p 5432:5432 -e POSTGRES_PASSWORD=cms -e POSTGRES_USER=cms --rm docker.tourmachine.tech/softmachine/postgres:16-alpine`
2. `mvn spring-boot:run`
