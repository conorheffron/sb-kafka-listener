package net.ironoc.kafka.listener

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doThrow
import org.slf4j.Logger
import kotlin.test.assertEquals

class MessageListenerTest {

    private lateinit var logger: Logger
    private lateinit var listener: MessageListener

    @BeforeEach
    fun setup() {
        logger = mock()
        listener = MessageListener(logger)
    }

    @Test
    fun `should log message details when record is valid`() {
        val record = ConsumerRecord("test-topic", 1, 42L, "myKey", "myValue")

        listener.listen(record)

        verify(logger).info(
            eq("Received message -> Topic: {}, Partition: {}, Offset: {}, Key: {}, Value: {}"),
            eq("test-topic"), eq(1), eq(42L), eq("myKey"), eq("myValue")
        )
    }

    @Test
    fun `should replace null key and value with string null`() {
        val record = ConsumerRecord<String, String>("topic-null", 0, 0L, null, null)

        listener.listen(record)

        verify(logger).info(
            eq("Received message -> Topic: {}, Partition: {}, Offset: {}, Key: {}, Value: {}"),
            eq("topic-null"), eq(0), eq(0L), eq("null"), eq("null")
        )
    }

    @Test
    fun `should log error when exception occurs`() {
        val badRecord = mock<ConsumerRecord<String, String>> {
            on { topic() } doThrow(RuntimeException("boom"))
        }

        listener.listen(badRecord)

        argumentCaptor<Exception>().apply {
            verify(logger).error(eq("Error processing Kafka message"), capture())
            assertEquals("boom", firstValue.message)
        }
    }
}