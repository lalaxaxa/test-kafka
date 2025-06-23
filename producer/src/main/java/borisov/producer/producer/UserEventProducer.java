package borisov.producer.producer;

import borisov.producer.config.KafkaTopics;
import borisov.producer.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    private final KafkaTopics kafkaTopics;

    @Autowired
    public UserEventProducer(KafkaTemplate<String, UserEvent> kafkaTemplate,
                             KafkaTopics kafkaTopics) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopics = kafkaTopics;
    }

    public void sendEvent(UserEvent userEvent) {
        CompletableFuture<SendResult<String, UserEvent>> future =
                kafkaTemplate.send(kafkaTopics.getUserEvent(), String.valueOf(userEvent.getId()), userEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Ошибка отправки сообщения: {}", exception.getMessage());
            } else {
                log.info("Сообщение отправлено успешно: Topic={} Partition={}, Offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}
