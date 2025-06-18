package borisov.producer.config;

import borisov.producer.event.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.custom.enabled-java-config", havingValue = "true", matchIfMissing = false)
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;
    private final CustomKafkaProperties customKafkaProperties;

    Map<String, Object> producerConfigs() {
        log.info("Kafka use java-configuration");
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

    @Bean
    public NewTopic createTopic(){
        CustomKafkaProperties.Topic topic = customKafkaProperties.getTopic();
        return TopicBuilder
                .name(topic.getUserEvent())
                .partitions(topic.getPartitions())
                .replicas(topic.getReplicas())
                .configs(Map.of(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG,
                        topic.getConfigs().get(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG)))
                .build();
    }

}
