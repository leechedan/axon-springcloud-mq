package org.github.axon.tag.common.autoconfig;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema;
import org.axonframework.extensions.kafka.eventhandling.DefaultKafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.github.axon.tag.common.continuance.common.GenericDomainEventGateway;
import org.github.axon.tag.common.continuance.common.KafkaMessageSourceConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
@ConditionalOnClass(name = "org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter")
public class KafkaAutoConfiguration {

    @Value("${axon.kafka.bootstrap-servers}")
    String bootstrapAddress;

    @ConditionalOnMissingBean
    @Bean
    public KafkaMessageConverter<String, byte[]> kafkaMessageConverter(
            @Qualifier("eventSerializer") Serializer eventSerializer) {
        log.debug("{}", eventSerializer);
        return DefaultKafkaMessageConverter.builder().serializer(eventSerializer).build();
    }


    @Bean
    public DefaultKafkaProducerFactory<String, byte[]> paymentProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String, byte[]>(configProps);
    }

    @Bean
    public KafkaTemplate<String, byte[]> paymentKafkaTemplate() {
        return new KafkaTemplate<>(paymentProducerFactory());
    }

    @Bean
    public KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer() {
        return new KafkaMessageSourceConfigurer();
    }

    @Bean
    public EventSchema eventSchema() {
        return EventSchema.builder()
                          .eventTable("DOMAIN_EVENT_ENTRY")
                          .snapshotTable("SNAPSHOT_EVENT_ENTRY")
                          .build();
    }

    @Bean
    public EventGateway itemEventGateway(EventBus itemEventStore) {
        return GenericDomainEventGateway.builder().eventBus(itemEventStore).build();
    }
}