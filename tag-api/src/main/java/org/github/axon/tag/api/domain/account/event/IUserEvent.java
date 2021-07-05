package org.github.axon.tag.api.domain.account.event;

import org.github.axon.tag.api.domain.account.event.AccountCreatedEvent;
import org.github.axon.tag.api.domain.account.event.BalanceUpdatedEvent;
import org.github.axon.tag.api.domain.account.event.MoneyDepositedEvent;
import org.github.axon.tag.api.domain.account.event.MoneyWithdrawnEvent;
import org.axonframework.eventhandling.ReplayStatus;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * 监听器接口
 * @author lijc
 * @date 2021-06-30
 */
public interface IUserEvent {

    void on(AccountCreatedEvent event);

    void on(BalanceUpdatedEvent event);

    void on(MoneyDepositedEvent event);

    void on(MoneyWithdrawnEvent event);
}
