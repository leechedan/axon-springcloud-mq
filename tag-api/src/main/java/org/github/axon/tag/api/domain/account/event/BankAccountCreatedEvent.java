package org.github.axon.tag.api.domain.account.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountCreatedEvent {

    private String id;
    private String name;
    private BigDecimal balance;

}
