package org.github.axon.tag.user.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.eventsourcing.AggregateSnapshotter;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.github.axon.tag.common.continuance.common.CommandInterceptor;
import org.github.axon.tag.common.continuance.common.CommandRetryScheduler;
import org.github.axon.tag.common.continuance.common.CustomEventSourcingRepository;
import org.github.axon.tag.user.domain.user.BankTransferAggregate;
import org.github.axon.tag.user.domain.user.UserAggregate;
import org.github.axon.tag.user.domain.user.UserCommandGateway;
import org.github.axon.tag.user.domain.user.saga.BankTransferSaga;
import org.github.axon.tag.user.domain.user.upcaster.UserEventUpCaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Slf4j
@Configuration
public class UserAxonConfig {

    @Bean
    public CustomEventSourcingRepository<BankTransferAggregate> bankTransferAggregateRepository(
            EmbeddedEventStore eventStore,
            SnapshotTriggerDefinition snapshotTriggerDefinition,
            ParameterResolverFactory parameterResolverFactory) {
        return CustomEventSourcingRepository.builder(BankTransferAggregate.class)
                                            .eventStore(eventStore)
                                            .snapshotTriggerDefinition(snapshotTriggerDefinition)
                                            .parameterResolverFactory(parameterResolverFactory)
                                            .build();
    }

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



    @Bean("completeTimer")
    public Timer completeTimer(MeterRegistry meterRegistry) {
        Timer timer = Timer.builder("sage.timer")
                           .tag("type", "complete")
                           .tag("payloadType", BankTransferSaga.class.getSimpleName())
                           .publishPercentiles(0.5, 0.95, 0.99) // median and 95th percentile
                           .publishPercentileHistogram()
                           .register(meterRegistry);
        return timer;
    }

    @Bean("cancelTimer")
    public Timer getCancelTimer(MeterRegistry meterRegistry) {
        Timer timer = Timer.builder("sage.timer")
                           .tag("type", "cancel")
                           .tag("payloadType", BankTransferSaga.class.getSimpleName())
                           .publishPercentiles(0.5, 0.95, 0.99) // median and 95th percentile
                           .publishPercentileHistogram()
                           .register(meterRegistry);
        return timer;
    }
}
