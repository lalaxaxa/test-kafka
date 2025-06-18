package borisov.producer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka.custom")
@Getter
@Setter
public class CustomKafkaProperties {
//public class CustomKafkaProperties extends KafkaProperties {
//второй вариант, но тогда нужно (prefix = "spring.kafka") и всё внутрь объекта custom собирать

    private Topic topic;

    @Getter
    @Setter
    public static class Topic{
        private String userEvent;
        private int partitions;
        private int replicas;
        private Map<String, String> configs;
    }

}
