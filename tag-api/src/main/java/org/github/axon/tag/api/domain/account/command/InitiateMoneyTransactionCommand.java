package org.github.axon.tag.api.domain.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiateMoneyTransactionCommand implements Serializable {
    @TargetAggregateIdentifier
    String transactionId;
    Long sourceAccountId;
    Long targetAccountId;
    BigDecimal amount;
}
