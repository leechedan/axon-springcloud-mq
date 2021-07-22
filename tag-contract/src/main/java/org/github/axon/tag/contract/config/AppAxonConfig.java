package org.github.axon.tag.contract.config;

import org.github.axon.tag.common.continuance.common.CommandInterceptor;
import org.github.axon.tag.common.continuance.common.CommandRetryScheduler;
import org.github.axon.tag.common.continuance.common.CustomEventSourcingRepository;
import org.github.axon.tag.contract.domain.contract.ContractAggregate;
import org.github.axon.tag.contract.domain.contract.ContractCommandGateway;
import org.github.axon.tag.contract.domain.contract.upcaster.ContractEventUpCaster;
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

@Configuration
public class AppAxonConfig {

    @Bean
    public CustomEventSourcingRepository<ContractAggregate> ContractAggregateRepository(
                            EmbeddedEventStore eventStore,
                            SnapshotTriggerDefinition snapshotTriggerDefinition,
                            ParameterResolverFactory parameterResolverFactory) {
        return CustomEventSourcingRepository.builder(ContractAggregate.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .parameterResolverFactory(parameterResolverFactory)
                .build();
    }

    @Bean
    public AggregateSnapshotter snapshotter(EmbeddedEventStore eventStore, ParameterResolverFactory parameterResolverFactory) {
        return AggregateSnapshotter.builder()
                .eventStore(eventStore)
                .parameterResolverFactory(parameterResolverFactory)
                .aggregateFactories(Collections.singletonList(new GenericAggregateFactory<>(ContractAggregate.class)))
                .build();
    }

    @Bean
    public EventUpcaster userUpCaster() {
        return new ContractEventUpCaster();
    }

    @Bean
    public ContractCommandGateway getCommandGateway(SimpleCommandBus simpleCommandBus, CommandInterceptor commandInterceptor) {
        return CommandGatewayFactory.builder()
                .commandBus(simpleCommandBus)
                .retryScheduler(new CommandRetryScheduler())
                .dispatchInterceptors(commandInterceptor)
                .build()
                .createGateway(ContractCommandGateway.class);
    }
}
