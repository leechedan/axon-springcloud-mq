package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${createCommandPkgName}.${aggregate?cap_first}CreateCommand;
import ${removeCommandPkgName}.${aggregate?cap_first}RemoveCommand;
import ${updateCommandPkgName}.${aggregate?cap_first}UpdateCommand;
import ${queryCommandPkgName}.${aggregate?cap_first}QueryCommand;
import ${iCommandPkgName}.I${aggregate?cap_first}Command;
import ${(basePkgName)!}.BaseHandler;
import ${aggregatePkgName}.${aggregate?cap_first}Aggregate;
import ${(basePkgName)!}.BaseEventsDto;
import ${commonPkgName}.continuance.common.CustomEventSourcingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.modelling.command.LockAwareAggregate;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class ${aggregate?cap_first}QueryHandler {

    private final CustomEventSourcingRepository<${aggregate?cap_first}Aggregate> ${aggregate?uncap_first}AggregateRepository;

    @Autowired
    QueryGateway queryGateway;

    @Autowired
    EventStorageEngine orderStorageEngine;

    @QueryHandler
    public ${aggregate?cap_first}Aggregate on(${aggregate?cap_first}QueryCommand command) {
        LockAwareAggregate<${aggregate?cap_first}Aggregate, EventSourcedAggregate<${aggregate?cap_first}Aggregate>> lockAwareAggregate =
                ${aggregate?uncap_first}AggregateRepository.load(command.getId().toString(), command.getEndDate());
        return lockAwareAggregate.getWrappedAggregate().getAggregateRoot();
    }

    public ${aggregate?cap_first}Aggregate getOrder(${aggregate?cap_first}QueryCommand query) throws ExecutionException, InterruptedException {
        return queryGateway.query(query, ${aggregate?cap_first}Aggregate.class).get();
    }

    public List<BaseEventsDto> listEventsOfOrder(${aggregate?cap_first}QueryCommand query) {
        DomainEventStream domainEventStream = orderStorageEngine.readEvents(query.getId());
        return domainEventStream.asStream()
            .map(domainEventMessage -> BaseEventsDto.builder()
                .name(domainEventMessage.getPayloadType().getName())
                .payload(domainEventMessage.getPayload().toString())
                .sequenceNumber(domainEventMessage.getSequenceNumber())
                .timestamp(domainEventMessage.getTimestamp())
                .build())
        .collect(Collectors.toList());
    }

}
