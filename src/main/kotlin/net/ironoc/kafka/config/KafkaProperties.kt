package net.ironoc.kafka.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.kafka")
data class KafkaProperties(
    var bootstrapServers: String = "",
    var consumer: Consumer = Consumer()
) {
    data class Consumer(
        var groupId: String = "",
        var keyDeserializer: String = "",
        var valueDeserializer: String = "",
        var autoOffsetReset: String = ""
    )
}
