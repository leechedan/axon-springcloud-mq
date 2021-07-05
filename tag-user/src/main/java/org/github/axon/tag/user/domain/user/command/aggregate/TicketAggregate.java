package org.github.axon.tag.user.domain.user.command.aggregate;

import org.github.axon.tag.api.domain.ticket.command.TicketCreateCommand;
import org.github.axon.tag.api.domain.ticket.command.TicketRemoveCommand;
import org.github.axon.tag.api.domain.ticket.command.TicketUpdateCommand;
import org.github.axon.tag.api.domain.ticket.event.TicketCreatedEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketRemovedEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketUpdatedEvent;
import org.github.axon.tag.user.domain.user.command.listener.ITicketCommand;
import org.github.axon.tag.api.domain.ticket.event.ITicketEvent;
import org.github.axon.tag.base.domain.common.BaseAggregate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import java.io.Serializable;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

/**
 * 聚合根
 * @author lijc
 * @date 2021-06-30
 */
@Aggregate(snapshotTriggerDefinition = "snapshotTriggerDefinition")
@NoArgsConstructor
@Data
public class TicketAggregate extends BaseAggregate
    implements ITicketCommand, ITicketEvent
    {


    /**
     * lock_user  
     */
    
    private String lockUser;
    /**
     * name  
     */
    
    private String name;
    /**
     * owner  
     */
    
    private String owner;

    public TicketAggregate(TicketCreateCommand command, MetaData metaData) {
        if (StringUtils.isBlank(command.getId())) {
            command.setId(IdentifierFactory.getInstance().generateIdentifier());
        }
        TicketCreatedEvent event = new TicketCreatedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }


    @Override
    public void handle(TicketUpdateCommand command, MetaData metaData) {
        TicketUpdatedEvent event = new TicketUpdatedEvent();
        BeanUtils.copyProperties(this, event);
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    @Override
    public void remove(TicketRemoveCommand command, MetaData metaData) {
        TicketRemovedEvent event = new TicketRemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    // ----------------------------------------------------

    @Override
    @EventSourcingHandler
    public void on(TicketCreatedEvent event, MetaData metaData) {
        applyMetaData(metaData);
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(TicketUpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }


    @Override
    @EventSourcingHandler
    public void on(TicketRemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
