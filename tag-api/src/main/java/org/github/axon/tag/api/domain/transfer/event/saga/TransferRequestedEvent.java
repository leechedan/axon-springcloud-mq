package org.github.axon.tag.api.domain.transfer.event.saga;

import org.github.axon.tag.base.domain.common.AbstractCommand;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransferRequestedEvent {

    private Long transactionId;
    private Long sourceId;
    private Long destinationId;
    private BigDecimal amount;
}
