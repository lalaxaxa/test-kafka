package borisov.consumer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    ConsumerFactory<String, Object> consumeFactory(){
        Map<String, Object> config = new HashMap<>();
        KafkaProperties.Consumer consumer = kafkaProperties.getConsumer();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.getKeyDeserializer());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.getValueDeserializer());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE,
                consumer.getProperties().get(JsonDeserializer.VALUE_DEFAULT_TYPE));
        config.put(JsonDeserializer.TRUSTED_PACKAGES,
                consumer.getProperties().get(JsonDeserializer.TRUSTED_PACKAGES));
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS,
                consumer.getProperties().get(JsonDeserializer.USE_TYPE_INFO_HEADERS));
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumeFactory){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumeFactory);
        return  factory;
    }

}