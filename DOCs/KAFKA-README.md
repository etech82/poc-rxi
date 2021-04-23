
// Print out the topics
// You should see no topics listed
$ docker exec -t docker_kafka_1 kafka-topics --bootstrap-server localhost:9092 --list

// Create a topic input-events-store
$ docker exec -t docker_kafka_1 kafka-topics --bootstrap-server localhost:9092 --create --topic input-events-store --partitions 3 --replication-factor 1

// Describe topic input-events-store
$ docker exec -t docker_kafka_1 kafka-topics --bootstrap-server localhost:9092 --describe --topic input-events-store
Topic:t1	PartitionCount:3	ReplicationFactor:1	Configs:segment.bytes=1073741824
	Topic: t1	Partition: 0	Leader: 1001	Replicas: 1001	Isr: 1001
	Topic: t1	Partition: 1	Leader: 1001	Replicas: 1001	Isr: 1001
	Topic: t1	Partition: 2	Leader: 1001	Replicas: 1001	Isr: 1001

// Connect with the Kafka console consumer in another terminal
$ docker exec -t docker_kafka_1 kafka-console-consumer --bootstrap-server localhost:9092 --group jacek-japila-pl --topic input-events-store

// Connect with the Kafka console producer in one terminal
$ docker exec -it docker_kafka_1 kafka-console-producer --broker-list localhost:9092 --topic input-events-store
    
Per il bene di questo esempio, aggiorna il store microservizio per inviare un messaggio al alert microservizio
tramite Kafka, ogni volta che viene aggiornata un'entità negozio.
Nel storeprogetto, crea un messaggio AlertServiceper inviare i dettagli dell'evento.
Questo servizio creerà il payload e lo serializzerà in un JSON Stringe utilizzerà il Kafka predefinito
StringSerializere StringDeserializergià definito in application.yml.




