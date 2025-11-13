package net.ironoc.kafka.listener

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageListener {

    private val logger = LoggerFactory.getLogger(MessageListener::class.java)

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"], groupId = "\${spring.kafka.consumer.group-id}")
    fun listen(record: ConsumerRecord<String, String>) {
        try {
            val key = record.key() ?: "null"
            val value = record.value() ?: "null"
            val topic = record.topic()
            val partition = record.partition()
            val offset = record.offset()

            logger.info("Received message -> Topic: {}, Partition: {}, " +
                    "Offset: {}, Key: {}, Value: {}", topic, partition,
                offset, key, value)
        } catch (ex: Exception) {
            logger.error("Error processing Kafka message", ex)
        }
    }
}
