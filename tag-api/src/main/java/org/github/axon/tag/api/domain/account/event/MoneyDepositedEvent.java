package org.github.axon.tag.api.domain.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDepositedEvent implements BalanceUpdatedEvent {

    Long accountId;
    Long transactionId;
    BigDecimal amount;
    BigDecimal balance;
}
