package ${packageName}.${(moduleName)!}${(subPkgName)!};

import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.common.caching.JCacheAdapter;
import org.axonframework.eventsourcing.CachingEventSourcingRepository;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.Repository;
import ${commonPkgName}.callback.LogCommandCallback;
import ${commonPkgName}.continuance.common.CustomEventSourcingRepository;
import ${commonPkgName}.gateway.MetaDataGateway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ${aggregatePkgName}.${aggregate}Aggregate;

/**
 * @author ${author}
 * @date ${date}
 */
@Configuration
public class ${aggregate?cap_first}Config {

    @Bean
    @ConditionalOnBean(JCacheAdapter.class)
    public Repository<${aggregate?cap_first}Aggregate> ${aggregate?uncap_first}Repository(JCacheAdapter cacheAdapter, EventStore eventStore, Snapshotter snapshotter) {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(snapshotter, 1);

        return CachingEventSourcingRepository.builder(${aggregate?cap_first}Aggregate.class)
                .eventStore(eventStore)
                .cache(cacheAdapter)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .aggregateFactory(new GenericAggregateFactory<>(${aggregate?cap_first}Aggregate.class)).build();
    }


    @Bean
    public CustomEventSourcingRepository<${aggregate?cap_first}Aggregate> ${aggregate?uncap_first}AggregateRepository(EmbeddedEventStore eventStore,
            SnapshotTriggerDefinition snapshotTriggerDefinition,
            ParameterResolverFactory parameterResolverFactory) {
        return CustomEventSourcingRepository.builder(${aggregate?cap_first}Aggregate.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .parameterResolverFactory(parameterResolverFactory)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public MetaDataGateway localCommandGateway(DistributedCommandBus commandBus) {
        CommandGatewayFactory factory = CommandGatewayFactory.builder().commandBus(commandBus).build();
        factory.registerCommandCallback(new LogCommandCallback(), ResponseTypes.instanceOf(String.class));
        return factory.createGateway(MetaDataGateway.class);
    }
    @Bean
    @ConditionalOnMissingBean
    public EventUpcaster eventUpcaster() {
        CommandGatewayFactory factory = CommandGatewayFactory.builder().commandBus(commandBus).build();
        factory.registerCommandCallback(new LogCommandCallback(), ResponseTypes.instanceOf(String.class));
        return factory.createGateway(MetaDataGateway.class);
    }
}
