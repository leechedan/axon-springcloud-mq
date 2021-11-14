package org.github.axon.tag.user.domain.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.github.axon.tag.api.domain.account.command.CancelMoneyTransactionCommand;
import org.github.axon.tag.api.domain.account.command.CompleteMoneyTransactionCommand;
import org.github.axon.tag.api.domain.account.event.TransactionCancelledEvent;
import org.github.axon.tag.api.domain.common.enums.BankTransferStatus;
import org.github.axon.tag.api.domain.transfer.command.CompleteTransferCommand;
import org.github.axon.tag.api.domain.transfer.command.FailTransferCommand;
import org.github.axon.tag.api.domain.transfer.command.RequestTransferCommand;
import org.github.axon.tag.api.domain.transfer.event.TransferCompletedEvent;
import org.github.axon.tag.api.domain.transfer.event.TransferFailedEvent;
import org.github.axon.tag.api.domain.transfer.event.saga.TransferRequestedEvent;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Aggregate(snapshotTriggerDefinition = "snapshotTriggerDefinition")
public class BankTransferAggregate {

    @AggregateIdentifier
    @RoutingKey
    private Long identifier;
    private Long sourceId;
    private Long destinationId;
    private BigDecimal amount;
    private BankTransferStatus status;

    public BankTransferAggregate() {
        log.info("BankTransferAggregate empty constructor invoked");
    }

    @CommandHandler
    public BankTransferAggregate(RequestTransferCommand cmd) {

        Assert.notNull(cmd.getId(), "Transaction Id should not be empty or null.");
        Assert.notNull(cmd.getSourceId(), "Source Id should not be empty or null.");
        Assert.notNull(cmd.getDestinationId(), "Destination Id should not be empty or null.");
        Assert.isTrue(cmd.getAmount().doubleValue() > 0d, "Amount must be greater than zero.");

        apply(TransferRequestedEvent.builder()
                                    .amount(cmd.getAmount())
                                    .destinationId(cmd.getDestinationId())
                                    .sourceId(cmd.getSourceId())
                                    .transactionId(cmd.getId())
                                    .build());
    }

    @CommandHandler
    public void handle(CompleteTransferCommand cmd) {
        log.info("{}", cmd);
        Assert.notNull(cmd.getId(), "Transaction Id should not be empty or null.");
        apply(TransferCompletedEvent.builder()
                                    .identifier(cmd.getId())
                                    .build());
    }

    @CommandHandler
    public void handle(FailTransferCommand cmd) {
        log.info("{}", cmd);
        Assert.notNull(cmd.getId(), "Transaction Id should not be empty or null.");
        apply(new TransactionCancelledEvent(cmd.getId(), cmd.getAmount(), "fail"));
    }

    @CommandHandler
    public void handle(CancelMoneyTransactionCommand cmd) {
        apply(new TransactionCancelledEvent(cmd.getTransactionId(), cmd.getAmount(), "E5"));
    }

    @CommandHandler
    public void handle(CompleteMoneyTransactionCommand cmd) {
        apply(new TransferCompletedEvent(cmd.getTransactionId()));
    }

    @EventSourcingHandler
    public void on(TransferRequestedEvent event) {
        log.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.identifier = event.getTransactionId();
        this.destinationId = event.getDestinationId();
        this.sourceId = event.getSourceId();
        this.amount = event.getAmount();
        this.status = BankTransferStatus.STARTED;
        log.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(TransferCompletedEvent event) {
        log.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.status = BankTransferStatus.COMPLETED;
        log.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(TransferFailedEvent event) {
        log.info("Handling {} event:{}", event.getClass().getSimpleName(), event);
        this.status = BankTransferStatus.FAILED;
        log.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(TransactionCancelledEvent event) {
        log.info("{}", event);
        this.status = BankTransferStatus.FAILED;
    }
}
