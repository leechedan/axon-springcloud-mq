package org.github.axon.tag.api.domain.account.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMoneyBankAccountCommand {

    /**
     * 账户ID
     */
    @TargetAggregateIdentifier
    @RoutingKey
    private Long id;
    /**
     * 订单ID
     */
    private Long transactionId;

    /**
     * 订单金额
     */
    private BigDecimal value;

}