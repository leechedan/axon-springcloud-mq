package org.github.axon.tag.api.domain.account.event;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
public class AccountCreatedEvent {

    Long accountId;

    @NotBlank
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
