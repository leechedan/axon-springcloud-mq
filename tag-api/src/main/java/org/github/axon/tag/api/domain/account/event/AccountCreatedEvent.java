package org.github.axon.tag.api.domain.account.event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
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
