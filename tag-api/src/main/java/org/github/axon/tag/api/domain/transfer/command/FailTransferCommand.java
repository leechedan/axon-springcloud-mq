package org.github.axon.tag.api.domain.transfer.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.github.axon.tag.base.domain.common.AbstractCommand;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Getter
public class FailTransferCommand implements AbstractCommand {

    private Long id;
    final BigDecimal amount;
    final String reason;

}
