package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.quartz.QuartzDeadlineManager;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.github.axon.tag.common.continuance.common.CustomJpaEventStorageEngine;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@AutoConfigureBefore(AxonAutoConfiguration.class)
@EnableJpaRepositories("org.github.axon.tag.common.repository")
@EntityScan(basePackages = {
        "org.axonframework.eventhandling.tokenstore.jpa",
        "org.axonframework.modelling.saga.repository.jpa",
        "org.github.axon.tag.common.continuance.common"
})
public class AxonContinueAutoConfiguration {

    @Primary
    @Bean
    @ConditionalOnMissingBean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        log.info("1 springAggregateSnapshotterFactoryBean");
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    @ConditionalOnMissingBean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        log.info("1 snapshotTriggerDefinition");
        return new EventCountSnapshotTriggerDefinition(snapshotter, 5);
    }

    @Primary
    @Bean
    @ConditionalOnMissingBean
    public EventProcessingConfigurer eventProcessingConfigurer(EventProcessingConfigurer eventProcessingConfigurer) {
        eventProcessingConfigurer.usingTrackingEventProcessors();
        return eventProcessingConfigurer;
    }


    @Bean
    @ConditionalOnMissingBean
    public DeadlineManager deadlineManager(AxonConfiguration configuration, TransactionManager transactionManager,
                                           Scheduler scheduler) {
        return QuartzDeadlineManager.builder().scheduler(scheduler)
                                    .scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
                                    .transactionManager(transactionManager).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ListenerInvocationErrorHandler listenerInvocationErrorHandler() {
        return PropagatingErrorHandler.INSTANCE;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
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

}
