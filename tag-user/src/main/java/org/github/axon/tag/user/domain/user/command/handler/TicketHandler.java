package org.github.axon.tag.user.domain.user.command.handler;

import org.github.axon.tag.api.domain.ticket.command.TicketCreateCommand;
import org.github.axon.tag.api.domain.ticket.command.TicketRemoveCommand;
import org.github.axon.tag.api.domain.ticket.command.TicketUpdateCommand;
import org.github.axon.tag.user.domain.user.command.listener.ITicketCommand;
import org.github.axon.tag.base.domain.common.BaseHandler;
import org.github.axon.tag.user.domain.user.command.aggregate.TicketAggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijc
 * @date 2021-06-30
 */
@Component
public class TicketHandler extends  BaseHandler implements ITicketCommand {

    @Autowired
    private Repository<TicketAggregate> repository;

    @CommandHandler
    public void handle(TicketCreateCommand command, MetaData metaData) throws Exception {
        repository.newInstance(() -> new TicketAggregate(command, metaData));
    }

    @Override
    @CommandHandler
    public void handle(TicketUpdateCommand command, MetaData metaData) {
        Aggregate<TicketAggregate> target = repository.load(command.getId());
        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.handle(command, metaData));
    }

    @Override
    @CommandHandler
    public void remove(TicketRemoveCommand command, MetaData metaData) {
        Aggregate<TicketAggregate> target = repository.load(command.getId());
        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.remove(command, metaData));
    }
}
