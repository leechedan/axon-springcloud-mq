package org.github.axon.tag.api.domain.ticket.event;

import org.axonframework.messaging.MetaData;

import org.github.axon.tag.api.domain.ticket.event.TicketCreatedEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketRemovedEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketUpdatedEvent;

/**
 * 监听器接口
 * @author lijc
 * @date 2021-06-30
 */
public interface ITicketEvent {

    void on(TicketCreatedEvent event, MetaData metaData);

    void on(TicketUpdatedEvent event, MetaData metaData);

    //void on(TicketLoginedEvent event, MetaData metaData);

    void on(TicketRemovedEvent event, MetaData metaData);
}
