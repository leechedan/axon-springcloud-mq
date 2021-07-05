package org.github.axon.tag.api.domain.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCancelledEvent {
    Long transactionId;
    BigDecimal amount;
    String errorCode;
}
