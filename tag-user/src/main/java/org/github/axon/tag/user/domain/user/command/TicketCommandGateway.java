package org.github.axon.tag.user.domain.user.command;

import org.axonframework.commandhandling.gateway.Timeout;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.github.axon.tag.base.domain.common.AbstractCommand;
import org.github.axon.tag.user.domain.user.command.aggregate.TicketAggregate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface TicketCommandGateway {

    // fire and forget
    void send(AbstractCommand command);

    // method that will wait for a result for 10 seconds
    @Timeout(value = 6, unit = TimeUnit.SECONDS)
    Long sendAndWait(AbstractCommand command);

    // method that attaches meta data and will wait for a result for 10 seconds
    @Timeout(value = 6, unit = TimeUnit.SECONDS)
    TicketAggregate sendAndWait(AbstractCommand command,
                                @MetaDataValue("userId") String userId);

    // this method will also wait, caller decides how long
    void sendAndWait(AbstractCommand command, long timeout, TimeUnit unit)
            throws TimeoutException, InterruptedException;
}
