package net.ironoc.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaConsumerApp

fun main(args: Array<String>) {
    runApplication<KafkaConsumerApp>(*args)
}
