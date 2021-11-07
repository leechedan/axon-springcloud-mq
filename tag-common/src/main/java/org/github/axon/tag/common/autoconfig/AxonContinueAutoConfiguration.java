package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executors;

@Slf4j
@Configuration
@AutoConfigureBefore(AxonAutoConfiguration.class)
public class AxonContinueAutoConfiguration {

    @Primary
    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        log.debug("1 springAggregateSnapshotterFactoryBean");
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        log.debug("1 snapshotTriggerDefinition");
        return new EventCountSnapshotTriggerDefinition(snapshotter, 5);
    }

    @Primary
    @Bean
    public EventProcessingConfigurer eventProcessingConfigurer(EventProcessingConfigurer eventProcessingConfigurer) {
        eventProcessingConfigurer.usingTrackingEventProcessors();
        return eventProcessingConfigurer;
    }

    @Bean
    DeadlineManager deadlineManager(TransactionManager transactionManager, org.axonframework.config.Configuration config) {
        return SimpleDeadlineManager.builder()
                                    .scopeAwareProvider(new ConfigurationScopeAwareProvider(config))
                                    .transactionManager(transactionManager)
                                    .scheduledExecutorService(Executors.newScheduledThreadPool(1)).build();
    }

    @Bean
    public ListenerInvocationErrorHandler listenerInvocationErrorHandler() {
        return PropagatingErrorHandler.INSTANCE;
    }


}
