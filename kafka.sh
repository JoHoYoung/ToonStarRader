/Users/HY/Downloads/kafka_2.12-2.1.0/bin/kafka-topics.sh --zookeeper localhost:2181 --list
/Users/HY/Downloads/kafka_2.12-2.1.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
/Users/HY/Downloads/kafka_2.12-2.1.0/bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test
/Users/HY/Downloads/kafka_2.12-2.1.0/bin/kafka-topics.sh --create replication-factor 3 --zookeeper localhost:2181 --partitions 3 --topic test
/Users/HY/Downloads/kafka_2.12-2.1.0/bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic test