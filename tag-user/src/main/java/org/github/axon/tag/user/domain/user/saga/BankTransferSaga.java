package org.github.axon.tag.user.domain.user.saga;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.github.axon.tag.api.domain.account.command.BalanceCorrectionCommand;
import org.github.axon.tag.api.domain.account.command.CompleteMoneyTransactionCommand;
import org.github.axon.tag.api.domain.account.command.DepositMoneyCommand;
import org.github.axon.tag.api.domain.account.command.WithdrawMoneyCommand;
import org.github.axon.tag.api.domain.account.event.MoneyDepositedEvent;
import org.github.axon.tag.api.domain.account.event.MoneyWithdrawnEvent;
import org.github.axon.tag.api.domain.account.event.TransactionCancelledEvent;
import org.github.axon.tag.api.domain.account.event.TransactionCompletedEvent;
import org.github.axon.tag.api.domain.transfer.event.saga.TransferRequestedEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.axonframework.common.BuilderUtils.assertNonNull;
import static org.axonframework.modelling.saga.SagaLifecycle.end;

@ToString
@Slf4j
@Saga
@NoArgsConstructor
public class BankTransferSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient DeadlineManager deadlineManager;

    @Getter
    @Setter
    private Long sourceAccountId;
    @Getter
    @Setter
    private Long targetAccountId;
    @Getter
    @Setter
    private Long transactionId;
    @Getter
    @Setter
    private String deadlineId;

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransferRequestedEvent event) {

        log.info("In TransactionInitiatedEvent 启动转账 {}", event);
        assertNonNull(event.getSourceId(), "source not null in saga");
        assertNonNull(event.getDestinationId(), "target not null in saga");
        this.sourceAccountId = event.getSourceId();
        this.targetAccountId = event.getDestinationId();
        this.transactionId = event.getTransactionId();

        this.deadlineId = deadlineManager.schedule(Duration.ofSeconds(5), "transferDeadline");


        SagaLifecycle.associateWith("transactionId", transactionId);

        commandGateway.send(new WithdrawMoneyCommand(event.getSourceId(), transactionId, event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        log.info("In MoneyWithdrawnEvent  扣款 {} {}", this.targetAccountId, this.sourceAccountId);
        commandGateway.send(new DepositMoneyCommand(targetAccountId, event.getTransactionId(), event.getAmount()));
    }


    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        log.info("In MoneyDepositedEvent 充值");
        commandGateway.send(new CompleteMoneyTransactionCommand(transactionId));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionCompletedEvent event) {
        log.info("In TransactionCompletedEvent");
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionCancelledEvent event) {
        log.info("In TransactionCancelledEvent  转账失败");
        if (event.getErrorCode().equals("E2")) {
            // Generate Compensatory action
            commandGateway.send(new BalanceCorrectionCommand(sourceAccountId,
                                                             event.getTransactionId(),
                                                             event.getAmount()));
        }
        end();
    }

    @DeadlineHandler(deadlineName = "transferDeadline")
    public void on() {
        // handle the Deadline
        log.info("In the deadline - deadlineId [{}] ", deadlineId);
    }
}
