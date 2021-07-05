package org.github.axon.tag.user.domain.user.upcaster;

import lombok.extern.slf4j.Slf4j;
import org.github.axon.tag.common.continuance.common.SameEventUpcaster;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class UserEventUpCaster extends SingleEventUpcaster {
    private static List<SameEventUpcaster> upCasters = Arrays.asList(
            new UserCreatedEventUpCaster(),
            new BalanceUpdatedEventUpcaster(),
            new TransactionCancelledEventUpcaster(),
            new TransferCompletedEventUpcaster(),
            new TransferFailedEventUpcaster(),
            new TransferRequestedEventUpCaster(),
            new MoneyWithdrawnEventUpcaster()
    );

    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return upCasters.stream().anyMatch(o -> o.canUpcast(intermediateRepresentation));
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        SameEventUpcaster upCaster = upCasters.stream()
            .filter(o -> o.canUpcast(intermediateRepresentation))
            .findAny().orElseThrow(RuntimeException::new);
        log.info("{}", upCaster);
        return upCaster.doUpcast(intermediateRepresentation);
    }
}
