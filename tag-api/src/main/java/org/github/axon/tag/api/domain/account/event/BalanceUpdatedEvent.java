package org.github.axon.tag.api.domain.account.event;

import java.math.BigDecimal;

public interface BalanceUpdatedEvent {

    Long getAccountId();

    BigDecimal getBalance();
}
