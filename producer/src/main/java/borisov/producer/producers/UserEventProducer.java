package borisov.producer.producers;

import borisov.producer.events.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    private final String topicName;

    @Autowired
    public UserEventProducer(KafkaTemplate<String, UserEvent> kafkaTemplate,
                             @Value("${custom.kafka.topics.user-events}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendEvent(UserEvent userEvent) {
        CompletableFuture<SendResult<String, UserEvent>> future =
                kafkaTemplate.send(topicName, String.valueOf(userEvent.getId()), userEvent);

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
