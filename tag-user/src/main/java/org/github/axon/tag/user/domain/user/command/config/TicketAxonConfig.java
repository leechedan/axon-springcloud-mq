package org.github.axon.tag.user.domain.user.command.config;

import org.github.axon.tag.common.continuance.common.*;
import org.github.axon.tag.user.domain.user.command.aggregate.TicketAggregate;
import org.github.axon.tag.user.domain.user.command.TicketCommandGateway;
import org.github.axon.tag.user.domain.user.upcaster.TicketEventUpcaster;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Slf4j
@Configuration
public class TicketAxonConfig {

    @Bean
    public CustomEventSourcingRepository<TicketAggregate> ticketAggregateRepository(
        EmbeddedEventStore eventStore,
        SnapshotTriggerDefinition snapshotTriggerDefinition,
        ParameterResolverFactory parameterResolverFactory) {
        return CustomEventSourcingRepository.builder(TicketAggregate.class)
        .eventStore(eventStore)
        .snapshotTriggerDefinition(snapshotTriggerDefinition)
        .parameterResolverFactory(parameterResolverFactory)
        .build();
    }

    @Bean
    public AggregateSnapshotter ticketSnapshotter(EmbeddedEventStore eventStore,
    ParameterResolverFactory parameterResolverFactory) {
        return AggregateSnapshotter.builder()
        .eventStore(eventStore)
        .parameterResolverFactory(parameterResolverFactory)
        .aggregateFactories(Collections.singletonList(
        new GenericAggregateFactory<>(TicketAggregate.class)))
        .build();
    }

    @Bean
    public EventUpcaster ticketUpcaster() {
        return new TicketEventUpcaster();
    }

    @Bean
    public TicketCommandGateway ticketCommandGateway(SimpleCommandBus simpleCommandBus,
    CommandInterceptor commandInterceptor) {
        return CommandGatewayFactory.builder()
        .commandBus(simpleCommandBus)
        .retryScheduler(new CommandRetryScheduler())
        .dispatchInterceptors(commandInterceptor)
        .build()
        .createGateway(TicketCommandGateway.class);
    }
}
