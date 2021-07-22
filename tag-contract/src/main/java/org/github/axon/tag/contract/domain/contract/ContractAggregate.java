package org.github.axon.tag.contract.domain.contract;

import org.github.axon.tag.api.domain.contract.command.CreateContractCommand;
import org.github.axon.tag.api.domain.contract.command.DeleteContractCommand;
import org.github.axon.tag.api.domain.contract.command.UpdateContractCommand;
import org.github.axon.tag.api.domain.contract.event.ContractCreatedEvent;
import org.github.axon.tag.api.domain.contract.event.ContractDeletedEvent;
import org.github.axon.tag.api.domain.contract.event.ContractUpdatedEvent;
import org.github.axon.tag.common.helper.UIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Aggregate(snapshotTriggerDefinition = "snapshotTriggerDefinition")
@Slf4j
public class ContractAggregate implements ContractInterface {

    @AggregateIdentifier
    private Long identifier;

    private String name;

    private String partyA;

    private String partyB;

    private String industryName;

    private boolean deleted = false;

    @CommandHandler
    public ContractAggregate(CreateContractCommand command, MetaData metaData) {
        log.info("command {}", command);
        AggregateLifecycle.apply(new ContractCreatedEvent(command.getIdentifier(),
            command.getName(),
            command.getPartyA(),
            command.getPartyB(),
            command.getIndustryName()), metaData);
    }

    @CommandHandler
    private void on(UpdateContractCommand command, MetaData metaData) {
        log.info("command {}", command);
        AggregateLifecycle.apply(new ContractUpdatedEvent(command.getIdentifier(),
                command.getName(),
                command.getPartyA(),
                command.getPartyB(),
                command.getIndustryName()),
            metaData);
    }

    @CommandHandler
    private void on(DeleteContractCommand command, MetaData metaData) {
        log.info("command {}", command);
        AggregateLifecycle.apply(new ContractDeletedEvent(command.getIdentifier()), metaData);
    }

    @EventSourcingHandler
    @AllowReplay(false)
    private void on(ContractCreatedEvent event) {
        log.info("event {}", event);
        this.setIdentifier(event.getIdentifier());
        this.onUpdate(event);
    }

    @EventSourcingHandler
    private void onUpdate(ContractUpdatedEvent event) {
        log.info("event {}", event);
        this.setName(event.getName());
        this.setPartyA(event.getPartyA());
        this.setPartyB(event.getPartyB());
        this.setIndustryName(event.getIndustryName());
    }

    @EventSourcingHandler(payloadType = ContractDeletedEvent.class)
    private void on() {
        this.setDeleted(true);
    }
}
