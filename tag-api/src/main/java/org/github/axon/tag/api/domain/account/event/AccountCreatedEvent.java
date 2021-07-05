package org.github.axon.tag.api.domain.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountCreatedEvent {

    Long accountId;

    String accountHolderName;

    BigDecimal amount;

    public AccountCreatedEvent(Long accountId,
            String accountHolderName, BigDecimal amount){
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.amount = amount;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}
