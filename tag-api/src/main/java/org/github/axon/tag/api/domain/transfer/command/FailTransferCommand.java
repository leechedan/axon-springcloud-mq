package org.github.axon.tag.api.domain.transfer.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Getter
public class FailTransferCommand {

    @TargetAggregateIdentifier
    Long identifier;

    BigDecimal amount;
}
