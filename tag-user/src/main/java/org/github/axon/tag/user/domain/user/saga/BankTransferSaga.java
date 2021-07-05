package org.github.axon.tag.user.domain.user.saga;

import lombok.Setter;
import org.github.axon.tag.api.domain.account.event.*;
import org.github.axon.tag.api.domain.account.command.*;
import org.github.axon.tag.api.domain.transfer.event.saga.TransferRequestedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

        log.debug("In TransactionInitiatedEvent 启动转账");
        assertNonNull(event.getSourceId(), "source not null in saga");
        assertNonNull(event.getDestinationId(), "target not null in saga");
        this.sourceAccountId = event.getSourceId();
        this.targetAccountId = event.getDestinationId();
        this.transactionId = event.getTransactionId();

        this.deadlineId = deadlineManager.schedule(Duration.ofSeconds(5), "transferDeadline");


        SagaLifecycle.associateWith("transactionId", transactionId);

        commandGateway.send(new WithdrawMoneyCommand(event.getSourceId(), transactionId, event.getAmount())
                /*,new FutureCallback<WithdrawMoneyCommand, Object>() {
                    @Override
                    public void onResult(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                        if (commandResultMessage.isExceptional()) {
                            log.info("{}", commandResultMessage);
                            commandGateway.send(new CancelMoneyTransactionCommand(event.getDestinationId(), event.getAmount(), "E1"));
                        }
                    }
                }*/);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        log.debug("In MoneyWithdrawnEvent  扣款 {} {}", this.targetAccountId, this.sourceAccountId);
        commandGateway.send(new DepositMoneyCommand(targetAccountId, event.getTransactionId(), event.getAmount())
                /*,new FutureCallback<DepositMoneyCommand, Object>() {
                    @Override
                    public void onResult(CommandMessage<? extends DepositMoneyCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                        if (commandResultMessage.isExceptional()) {
                            commandGateway.send(new CancelMoneyTransactionCommand(event.getTransactionId(), event.getAmount(), "E2"));
                        }
                    }
                }*/);
    }



    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        log.debug("In MoneyDepositedEvent 充值");
        commandGateway.send(new CompleteMoneyTransactionCommand(transactionId));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionCompletedEvent event) {
        log.debug("In TransactionCompletedEvent");
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(TransactionCancelledEvent event) {
        log.debug("In TransactionCancelledEvent  转账失败");
        if(event.getErrorCode().equals("E2")) {
            // Generate Compensatory action
            commandGateway.send(new BalanceCorrectionCommand(sourceAccountId, event.getTransactionId(), event.getAmount()));
        }
        end();
    }

    @DeadlineHandler(deadlineName = "transferDeadline")
    public void on() {
        // handle the Deadline
        log.debug("In the deadline - deadlineId [{}] ", deadlineId);
    }
}
