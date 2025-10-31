package borisov.producer.config;


import event.UserEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    Map<String, Object> producerConfigs() {
        Map<String, Object> config = new HashMap<>();
        KafkaProperties.Producer producer = kafkaProperties.getProducer();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producer.getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producer.getValueSerializer());
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,
                producer.getProperties().get(JsonSerializer.ADD_TYPE_INFO_HEADERS));
        return config;
    }

    @Bean
    ProducerFactory<String, UserEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    KafkaTemplate<String, UserEvent> kafkaTemplate() {
        return new KafkaTemplate<String, UserEvent>(producerFactory());
    }


}
