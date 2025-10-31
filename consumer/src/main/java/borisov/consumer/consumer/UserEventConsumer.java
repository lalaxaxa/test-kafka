package borisov.consumer.consumer;


import borisov.consumer.service.UserEventsLogService;
import event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventConsumer {
    private  final UserEventsLogService userEventsLogService;

    @KafkaListener(topics = "#{@kafkaTopics.userEvent}")
    public void consume(ConsumerRecord<String, UserEvent> record){
        log.info("Получено сообщение из Kafka Topic={} Partition={}, Offset={}: {}",
                record.topic(),
                record.partition(),
                record.offset(),
                record.value());
        userEventsLogService.save(record.value());
    }

}
