package org.github.axon.tag.contract.domain.contract.handler;

import org.github.axon.tag.api.domain.account.event.AccountCreatedEvent;
import org.github.axon.tag.common.continuance.common.CustomEventSourcingRepository;
import org.github.axon.tag.contract.entity.ContractView;
import org.github.axon.tag.contract.entity.ContractViewRepository;
import org.github.axon.tag.contract.domain.contract.ContractAggregate;
import org.github.axon.tag.base.domain.common.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.modelling.command.LockAwareAggregate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.MessageFormat;

@Component
@Slf4j
@AllArgsConstructor
@Transactional
public class ContractViewHandler {

    private final CustomEventSourcingRepository<ContractAggregate> customEventSourcingRepository;

    private final ContractViewRepository contractViewRepository;


    /**
     * 任何 contract 事件发生之后，重新计算 aggregate 的最新状态，转换成 view 之后存储到本地
     *
     * @param event   any event from contract
     * @param message domain event wrapper
     */
    @EventHandler
    public void on(AbstractEvent event, DomainEventMessage<AbstractEvent> message) {


        log.info(MessageFormat.format("{0}: {1} , seq: {2}, payload: {3}", message.getType(), message.getAggregateIdentifier(), message.getSequenceNumber(), message.getPayload()));

        updateContractView(message.getAggregateIdentifier());
    }

    @Transactional
    public void updateContractView(String id) {
        LockAwareAggregate<ContractAggregate, EventSourcedAggregate<ContractAggregate>> lockAwareAggregate = customEventSourcingRepository.load(id);
        ContractAggregate aggregate = lockAwareAggregate.getWrappedAggregate().getAggregateRoot();


        ContractView contractView = contractViewRepository.findById(Long.valueOf(id)).orElse(new ContractView());
        contractView.setId(aggregate.getId());
        contractView.setDeleted(aggregate.isDeleted());
        contractView.setName(aggregate.getName());
        contractView.setPartyA(aggregate.getPartyA());
        contractView.setPartyB(aggregate.getPartyB());
        contractView.setIndustryName(aggregate.getIndustryName());
        contractViewRepository.save(contractView);
    }

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("----");
    }
}
