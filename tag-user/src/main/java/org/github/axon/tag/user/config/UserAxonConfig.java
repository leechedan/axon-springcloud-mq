package org.github.axon.tag.user.config;

import org.github.axon.tag.common.continuance.common.*;
import org.github.axon.tag.user.domain.user.UserAggregate;
import org.github.axon.tag.user.domain.user.UserCommandGateway;
import org.github.axon.tag.user.domain.user.upcaster.UserEventUpCaster;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
//import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
//import org.axonframework.extensions.kafka.KafkaProperties;
//import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
//import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;
//import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
//import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Slf4j
@Configuration
public class UserAxonConfig {

    @Bean
    public CustomEventSourcingRepository<UserAggregate> userAggregateRepository(
            EmbeddedEventStore eventStore,
            SnapshotTriggerDefinition snapshotTriggerDefinition,
            ParameterResolverFactory parameterResolverFactory) {
        return CustomEventSourcingRepository.builder(UserAggregate.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .parameterResolverFactory(parameterResolverFactory)
                .build();
    }

    @Bean
    public AggregateSnapshotter snapshotter(EmbeddedEventStore eventStore,
                                            ParameterResolverFactory parameterResolverFactory) {
        return AggregateSnapshotter.builder()
                .eventStore(eventStore)
                .parameterResolverFactory(parameterResolverFactory)
                .aggregateFactories(Collections.singletonList(
                        new GenericAggregateFactory<>(UserAggregate.class)))
                .build();
    }

    @Bean
    public EventUpcaster userUpCaster() {
        return new UserEventUpCaster();
    }

    @Bean
    public UserCommandGateway getCommandGateway(SimpleCommandBus simpleCommandBus,
                                                CommandInterceptor commandInterceptor) {
        return CommandGatewayFactory.builder()
                .commandBus(simpleCommandBus)
                .retryScheduler(new CommandRetryScheduler())
                .dispatchInterceptors(commandInterceptor)
                .build()
                .createGateway(UserCommandGateway.class);
    }
}
