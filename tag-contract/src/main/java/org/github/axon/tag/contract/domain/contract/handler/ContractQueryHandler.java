package org.github.axon.tag.contract.domain.contract.handler;

import org.github.axon.tag.api.domain.contract.command.GetContractAggregateQuery;
import org.github.axon.tag.api.domain.contract.dto.ContractAggregateEventsDto;
import org.github.axon.tag.common.continuance.common.CustomEventSourcingRepository;
import org.github.axon.tag.contract.domain.contract.ContractAggregate;
import org.github.axon.tag.api.domain.contract.command.QueryContractCommand;
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
public class ContractQueryHandler {
    private final CustomEventSourcingRepository<ContractAggregate> contractAggregateRepository;

    @QueryHandler
    public ContractAggregate on(QueryContractCommand command) {
        LockAwareAggregate<ContractAggregate, EventSourcedAggregate<ContractAggregate>> lockAwareAggregate = contractAggregateRepository.load(command.getId().toString(), command.getEndDate());
        return lockAwareAggregate.getWrappedAggregate().getAggregateRoot();
    }

        @Autowired
        QueryGateway queryGateway;

        @Autowired
        EventStorageEngine orderStorageEngine;

        public ContractAggregate getOrder(GetContractAggregateQuery query) throws ExecutionException, InterruptedException {
            return queryGateway.query(query, ContractAggregate.class).get();
        }

        public List<ContractAggregateEventsDto> listEventsOfOrder(GetContractAggregateQuery query) {
            DomainEventStream domainEventStream = orderStorageEngine.readEvents(query.getId());
            return domainEventStream.asStream()
                    .map(domainEventMessage -> ContractAggregateEventsDto.builder()
                            .name(domainEventMessage.getPayloadType().getName())
                            .payload(domainEventMessage.getPayload().toString())
                            .sequenceNumber(domainEventMessage.getSequenceNumber())
                            .timestamp(domainEventMessage.getTimestamp())
                            .build())
                    .collect(Collectors.toList());
        }



}
