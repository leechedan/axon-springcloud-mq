package org.github.axon.tag.api.domain.account.event.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountBalanceUpdatedEvent {

    /**
     * 订单号
     */
    private Long identifier;
    private Long transactionId;
    private BigDecimal balance;

}
