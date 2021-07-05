package org.github.axon.tag.common.autoconfig;


import org.github.axon.tag.common.continuance.common.CustomEmbeddedEventStore;
import org.github.axon.tag.common.continuance.common.CustomJpaEventStorageEngine;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.quartz.QuartzDeadlineManager;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration;
import org.axonframework.springboot.util.RegisterDefaultEntities;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Slf4j
@Configuration
@AutoConfigureAfter(JpaEventStoreAutoConfiguration.class)
@AutoConfigureBefore
@EnableJpaRepositories("org.github.axon.tag.common.repository")
@EntityScan(basePackages = {
        "org.axonframework.eventsourcing.eventstore.jpa",
        "org.axonframework.eventhandling.tokenstore.jpa",
        "org.axonframework.modelling.saga.repository.jpa",
        "org.github.axon.tag.common.continuance.common"
})
public class AxonContinueAutoConfiguration {

    @Primary
    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        log.info("1 springAggregateSnapshotterFactoryBean");
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        log.info("1 snapshotTriggerDefinition");
        return new EventCountSnapshotTriggerDefinition(snapshotter, 5);
    }

//    @Bean
    public EventStorageEngine eventStorageEngine(Serializer defaultSerializer,
                                                 PersistenceExceptionResolver persistenceExceptionResolver,
                                                 @Qualifier("eventSerializer") Serializer eventSerializer,
                                                 EntityManagerProvider entityManagerProvider,
                                                 EventUpcaster userUpCaster,
                                                 TransactionManager transactionManager) {
        return CustomJpaEventStorageEngine.builder()
            .snapshotSerializer(defaultSerializer)
            .upcasterChain(userUpCaster)
            .persistenceExceptionResolver(persistenceExceptionResolver)
            .eventSerializer(eventSerializer)
            .entityManagerProvider(entityManagerProvider)
            .transactionManager(transactionManager)
            .build();
    }

    @Primary
    @Bean
    public EventProcessingConfigurer eventProcessingConfigurer(EventProcessingConfigurer eventProcessingConfigurer) {
        eventProcessingConfigurer.usingTrackingEventProcessors();
        return eventProcessingConfigurer;
    }




    @Bean
    public DeadlineManager deadlineManager(AxonConfiguration configuration, TransactionManager transactionManager, Scheduler scheduler) {
        return QuartzDeadlineManager.builder().scheduler(scheduler)
                .scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
                .transactionManager(transactionManager).build();
    }

    @Bean
    public ListenerInvocationErrorHandler listenerInvocationErrorHandler() {
        return PropagatingErrorHandler.INSTANCE;
    }

}
