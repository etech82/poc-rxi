# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured Docker services

### Service registry and configuration server:

- [JHipster Registry](http://localhost:8761)

### Applications and dependencies:

- gateway (gateway application)
- gateway's mysql database
- inventory (microservice application)
- inventory's mysql database
- purchasing (microservice application)
- purchasing's mysql database
- replenishment (microservice application)
- replenishment's mongodb database

### Additional Services:

- Kafka
- Zookeeper
- [Prometheus server](http://localhost:9090)
- [Prometheus Alertmanager](http://localhost:9093)
- [Grafana](http://localhost:3000)
- [Keycloak server](http://localhost:9080)
