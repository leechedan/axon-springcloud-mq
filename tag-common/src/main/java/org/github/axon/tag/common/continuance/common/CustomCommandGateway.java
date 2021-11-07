package org.github.axon.tag.common.continuance.common;

import org.axonframework.commandhandling.gateway.Timeout;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.github.axon.tag.base.domain.common.AbstractCommand;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface CustomCommandGateway {

    // fire and forget
    void sendCommand(AbstractCommand command);

    // method that will wait for a result for 10 seconds
    @Timeout(value = 6, unit = TimeUnit.SECONDS)
    Long sendCommandAndWaitForAResult(AbstractCommand command);


    // method that will wait for a result for 10 seconds
    @Timeout(value = 6, unit = TimeUnit.SECONDS)
    void sendCommandAndWait(AbstractCommand command);

    // this method will also wait, caller decides how long
    void sendCommandAndWait(AbstractCommand command, long timeout, TimeUnit unit) throws TimeoutException, InterruptedException;
}
