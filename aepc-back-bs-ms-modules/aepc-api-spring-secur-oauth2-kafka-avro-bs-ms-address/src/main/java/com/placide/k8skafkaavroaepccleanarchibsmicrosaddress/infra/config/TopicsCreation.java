package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;

@Configuration
public class TopicsCreation {

    private static final List<String> TOPICS = List.of("avro-addresses-created", "avro-addresses-deleted", "avro-addresses-edited");
    private static final int NB_PARTITIONS = 1;
    private static final int NB_REPLICAS = 1;
    @Bean
    public NewTopic createAddressAddTopic() {
        return TopicBuilder
                .name(TOPICS.get(0))
                .partitions(NB_PARTITIONS)
                .replicas(NB_REPLICAS)
                .build();
    }

    @Bean
    public NewTopic createAddressDeleteTopic() {
        return TopicBuilder
                .name(TOPICS.get(1))
                .partitions(NB_PARTITIONS)
                .replicas(NB_REPLICAS)
                .build();
    }
    @Bean
    public NewTopic createAddressEditTopic(){
        return TopicBuilder
                .name(TOPICS.get(2))
                .partitions(NB_PARTITIONS)
                .replicas(NB_REPLICAS)
                .build();
    }
}
