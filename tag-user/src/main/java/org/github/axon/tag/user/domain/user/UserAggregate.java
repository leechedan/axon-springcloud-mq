package org.github.axon.tag.user.domain.user;

import org.github.axon.tag.api.domain.account.command.*;
import org.github.axon.tag.api.domain.account.event.*;
import org.github.axon.tag.api.domain.account.exception.DepositNotPermittedException;
import org.github.axon.tag.api.domain.account.exception.InsufficientBalanceException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.ReplayStatus;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@ToString
@Getter
@Setter
@AllArgsConstructor
@Aggregate(snapshotTriggerDefinition = "snapshotTriggerDefinition")
@Slf4j
@NoArgsConstructor
public class UserAggregate implements IUserEvent {

    @AggregateIdentifier
    private Long accountId;
    private BigDecimal balance;
    private String accountHolderName;
    private Boolean deleted;

    @CommandHandler
    public UserAggregate(CreateAccountCommand cmd) {
        log.debug("In CreateAccountCommand 创建聚合体");
        this.accountId = cmd.getAccountId();
        apply(new AccountCreatedEvent(cmd.getAccountId(), cmd.getAccountHolderName(), BigDecimal.TEN));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand cmd) {
        log.debug("In WithdrawMoneyCommand 聚合体{}扣款", accountId);
        if (balance.intValue() < cmd.getAmount().intValue()) {
            apply(new TransactionCancelledEvent(cmd.getTransactionId(), BigDecimal.ZERO, "E4"));
//            throw new InsufficientBalanceException("Insufficient Amount");
        }
        apply(new MoneyWithdrawnEvent(accountId, cmd.getTransactionId(), cmd.getAmount(), balance.subtract(cmd.getAmount())));
    }

    @CommandHandler
    public void handle(DepositMoneyCommand cmd) {
        log.debug("In DepositMoneyCommand 聚合体{}充值", accountId);
        // Following is just a hypothetical business rule for demo purpose
//        if ((balance.intValue() + cmd.getAmount().intValue()) > 100) {
            //禁止异常，应该发出事件通知
//            throw new DepositNotPermittedException("Too much balance 转入后总金额超过100");
//        }
        apply(new MoneyDepositedEvent(cmd.getAccountId(), cmd.getTransactionId(), cmd.getAmount(), balance.add(cmd.getAmount())));
    }

    @CommandHandler
    public void handle(BalanceCorrectionCommand cmd) {
        log.debug("In BalanceCorrectionCommand");
        apply(new MoneyDepositedEvent(cmd.getAccountId(), cmd.getTransactionId(), cmd.getAmount(), balance .add(cmd.getAmount())));
    }

    @Override
    @EventSourcingHandler
    @AllowReplay(false)
    public void on(AccountCreatedEvent event) {
        log.debug("In AccountCreatedEvent:{}", event);
        Assert.notNull(event.getAccountId(), "ACCOUNT Id should not be empty or null.");
        this.accountId = event.getAccountId();
        this.accountHolderName = event.getAccountHolderName();
        this.balance = BigDecimal.TEN;
    }

    @Override
    @EventSourcingHandler
    public void on(BalanceUpdatedEvent event) {
        log.debug("In BalanceUpdatedEvent 聚合体{}更新", accountId);
        this.balance = event.getBalance();
    }

    @Override
    @EventSourcingHandler
    public void on(MoneyDepositedEvent event){
        this.balance = this.balance.add(event.getAmount());
    }

    @Override
    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event) {
        log.info("{}", event);
        this.balance = this.balance.add(event.getAmount());

    }
}

