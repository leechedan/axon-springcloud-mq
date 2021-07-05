package org.github.axon.tag.common.autoconfig;

import org.github.axon.tag.common.continuance.common.GenericDomainEventGateway;
import org.github.axon.tag.common.continuance.common.KafkaMessageSourceConfigurer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.config.Configurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema;
import org.axonframework.extensions.kafka.KafkaProperties;
import org.axonframework.extensions.kafka.eventhandling.DefaultKafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
@ConditionalOnProperty(prefix = "axon.kafka", name = "enable", havingValue = "true", matchIfMissing = true)
public class KafkaAutoConfiguration {

    @Value("${axon.kafka.bootstrap-servers}")
    String bootstrapAddress;

    @ConditionalOnMissingBean
    @Bean
    public KafkaMessageConverter<String, byte[]> kafkaMessageConverter(
            @Qualifier("eventSerializer") Serializer eventSerializer) {
        log.info("{}", eventSerializer);
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


    /**
     * 每个领域上下文需要监听一个队列
     * @return
     */
    public ConsumerFactory<String, byte[]> greetingConsumerFactory(String topic) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, topic);
        JsonDeserializer<byte[]> customDeserializer = new JsonDeserializer<>(byte[].class);
        customDeserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<String, byte[]>(props, new StringDeserializer(), customDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> paymentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(greetingConsumerFactory("invoiceTopic"));
        return factory;
    }

    @Bean
    @Qualifier("saga")
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> sagaKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(greetingConsumerFactory("saga"));
        return factory;
    }

    /**
     * spring config
     * axon:
     *   eventhandling.processors:
     *     BankAccountQueryListener:
     *       source: menuMessageSource
     *       mode: tracking
     *   BankTransferProcessListener:
     *     mode: tracking
     *     source: menuMessageSource
     * 以下是event handling config**/
    /**
     * group-id和application-name相关
     * @param kafkaProperties
     * @param consumerAxonFactory
     * @param fetcher
     * @param kafkaMessageConverter
     * @param kafkaMessageSourceConfigurer
     * @param configurer
     * @param connectionProvider
     * @param eventSchema
     * @param itemEventGateway
     * @return
     */
    @Bean
    public SubscribableKafkaMessageSource<String, byte[]> menuMessageSource(
            KafkaProperties kafkaProperties,
            org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory<String, byte[]> consumerAxonFactory,
            Fetcher<String, byte[], EventMessage<?>> fetcher,
            KafkaMessageConverter<String, byte[]> kafkaMessageConverter,
            KafkaMessageSourceConfigurer kafkaMessageSourceConfigurer,
            Configurer configurer,
            ConnectionProvider connectionProvider,
            EventSchema eventSchema,
            EventGateway itemEventGateway) {
        log.info("init menuMessageSource");
        SubscribableKafkaMessageSource<String, byte[]> subscribableKafkaMessageSource = SubscribableKafkaMessageSource.<String, byte[]>builder()
                .topics(Arrays.asList(kafkaProperties.getDefaultTopic()))
                .groupId("contract")
                .consumerFactory(consumerAxonFactory)
                .fetcher(fetcher)
                .messageConverter(kafkaMessageConverter)
                .consumerCount(1)
                .autoStart()
                .build();

        SubscribingKafkaEventProcessor processor = SubscribingKafkaEventProcessor.Builder()
//                .setAppendEventsFunction(JdbcEventStorageEngineStatements::appendEvents)
                .setConnectionProvider(connectionProvider)
                .setEventSchema(eventSchema)
                .build();

        subscribableKafkaMessageSource.subscribe(processor.buildKafkaProcessingFunction());

        kafkaMessageSourceConfigurer.registerSubscribableSource(configuration -> subscribableKafkaMessageSource);
        configurer.registerModule(kafkaMessageSourceConfigurer);
        return subscribableKafkaMessageSource;
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