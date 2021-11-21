package org.github.axon.tag.api.domain.transfer.command;

import org.github.axon.tag.base.domain.common.AbstractCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransferCommand implements AbstractCommand {

    private Long id;
    private Long sourceId;
    private Long destinationId;
    private BigDecimal amount;

}
