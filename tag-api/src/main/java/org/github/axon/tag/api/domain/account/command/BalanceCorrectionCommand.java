package org.github.axon.tag.api.domain.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceCorrectionCommand {

    @TargetAggregateIdentifier
    Long accountId;

    Long transactionId;

    BigDecimal amount;
}
