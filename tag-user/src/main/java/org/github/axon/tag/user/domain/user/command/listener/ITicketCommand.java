package org.github.axon.tag.user.domain.user.command.listener;

import org.github.axon.tag.api.domain.ticket.command.TicketCreateCommand;
import org.github.axon.tag.api.domain.ticket.command.TicketRemoveCommand;
import org.github.axon.tag.api.domain.ticket.command.TicketUpdateCommand;
import org.axonframework.messaging.MetaData;

/**
 * @author lijc
 * @date 2021-06-30
 */
public interface ITicketCommand {

    void handle(TicketUpdateCommand command, MetaData metaData);

    //void handle(TicketCreateCommand command, MetaData metaData);

    void remove(TicketRemoveCommand command, MetaData metaData);
}
