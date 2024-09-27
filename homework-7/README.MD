# Start application sequence

1. Build project
```shell
mvn clean install
```

2. Build image and run docker containers
```shell
docker-compose up --build -d
```