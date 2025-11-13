package net.ironoc.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaDemoApplication

fun main(args: Array<String>) {
    runApplication<KafkaDemoApplication>(*args)
}
