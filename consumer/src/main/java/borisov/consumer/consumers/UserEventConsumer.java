package borisov.consumer.consumers;

import borisov.consumer.events.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventConsumer {

    @KafkaListener(topics = "${custom.kafka.topics.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, UserEvent> record){
        log.info("Получено сообщение из Kafka: {}", record.value());
    }

}
