package borisov.producer.config;

import borisov.producer.events.UserEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "enabled-java-config", havingValue = "true", matchIfMissing = true)
public class KafkaConfig {
    @Autowired
    Environment environment;

    Map<String, Object> producerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.key-serializer"));
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.value-serializer"));
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, environment.getProperty("spring.kafka.producer.properties.spring.json.add.type.headers"));
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
    public NewTopic createTopic(@Value("${custom.kafka.topics.user-events}") String topicName){

        return TopicBuilder
                .name(topicName)
                .partitions(3)
                .replicas(1)
                .configs(Map.of("min.insync.replicas","1"))
                .build();
    }

}
