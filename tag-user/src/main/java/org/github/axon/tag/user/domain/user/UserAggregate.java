package org.github.axon.tag.user.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.github.axon.tag.api.domain.account.command.BalanceCorrectionCommand;
import org.github.axon.tag.api.domain.account.command.CreateAccountCommand;
import org.github.axon.tag.api.domain.account.command.DepositMoneyCommand;
import org.github.axon.tag.api.domain.account.command.WithdrawMoneyCommand;
import org.github.axon.tag.api.domain.account.event.AccountCreatedEvent;
import org.github.axon.tag.api.domain.account.event.BalanceUpdatedEvent;
import org.github.axon.tag.api.domain.account.event.IUserEvent;
import org.github.axon.tag.api.domain.account.event.MoneyDepositedEvent;
import org.github.axon.tag.api.domain.account.event.MoneyWithdrawnEvent;
import org.github.axon.tag.api.domain.account.event.TransactionCancelledEvent;
import org.github.axon.tag.api.domain.account.event.TransactionCompletedEvent;
import org.github.axon.tag.api.domain.activation.ExpireActivationCommand;
import org.github.axon.tag.user.entity.BankTransferEntry;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Long, BankTransferEntry> incompleteFrom = new HashMap<>();
    private Map<Long, BankTransferEntry> incompleteTo = new HashMap<>();

    @CommandHandler
    public UserAggregate(CreateAccountCommand cmd) {
        log.debug("In CreateAccountCommand 创建聚合体");
        this.accountId = cmd.getId();
        apply(new AccountCreatedEvent(cmd.getId(), cmd.getAccountHolderName(), BigDecimal.TEN));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand cmd) {
        log.debug("In WithdrawMoneyCommand 聚合体{}扣款", accountId);
        if (balance.intValue() < cmd.getAmount().intValue()) {
            apply(new TransactionCancelledEvent(cmd.getTransactionId(), BigDecimal.ZERO, "E4"));
//            throw new InsufficientBalanceException("Insufficient Amount");
        }
        apply(new MoneyWithdrawnEvent(accountId,
                                      cmd.getTransactionId(),
                                      cmd.getAmount(),
                                      balance.subtract(cmd.getAmount())));
    }

    @CommandHandler
    public void handle(DepositMoneyCommand cmd) {
        log.debug("In DepositMoneyCommand 聚合体{}充值", accountId);
        // Following is just a hypothetical business rule for demo purpose
//        if ((balance.intValue() + cmd.getAmount().intValue()) > 100) {
        //禁止异常，应该发出事件通知
//            throw new DepositNotPermittedException("Too much balance 转入后总金额超过100");
//        }
        apply(new MoneyDepositedEvent(cmd.getAccountId(),
                                      cmd.getTransactionId(),
                                      cmd.getAmount(),
                                      balance.add(cmd.getAmount())));
    }

    @CommandHandler
    public void on(ExpireActivationCommand cmd) {
        log.info("too late to activation on {}", cmd.getId());
    }

    @CommandHandler
    public void handle(BalanceCorrectionCommand cmd) {
        log.debug("In BalanceCorrectionCommand");
        apply(new MoneyDepositedEvent(cmd.getAccountId(),
                                      cmd.getTransactionId(),
                                      cmd.getAmount(),
                                      balance.add(cmd.getAmount())));
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
    public void on(MoneyDepositedEvent event) {
        log.info("{}", event);
        BankTransferEntry bankTransferEntry = new BankTransferEntry();
        bankTransferEntry.setTransactionId(event.getTransactionId());
        bankTransferEntry.setAmount(event.getAmount());
        incompleteTo.put(bankTransferEntry.getTransactionId(), bankTransferEntry);
        this.balance = this.balance.add(event.getAmount());
    }

    @Override
    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event) {
        log.info("{}", event);
        BankTransferEntry bankTransferEntry = new BankTransferEntry();
        bankTransferEntry.setTransactionId(event.getTransactionId());
        bankTransferEntry.setAmount(event.getAmount());
        incompleteFrom.put(bankTransferEntry.getTransactionId(), bankTransferEntry);
        this.balance = this.balance.add(event.getAmount().negate());
    }

    @EventSourcingHandler
    public void on(TransactionCompletedEvent event) {
        log.info("{}", event);
        incompleteFrom.remove(event.getTransactionId());
        incompleteTo.remove(event.getTransactionId());
    }
}

