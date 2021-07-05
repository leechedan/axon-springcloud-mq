package org.github.axon.tag.contract.service;


import org.github.axon.tag.api.domain.account.event.BankAccountRemovedEvent;
import org.github.axon.tag.api.domain.account.event.TransactionCancelledEvent;
import org.github.axon.tag.api.domain.account.event.TransactionCompletedEvent;
import org.github.axon.tag.api.domain.account.event.TransactionInitiatedEvent;
import org.github.axon.tag.api.domain.common.enums.BankTransferStatus;
import org.github.axon.tag.api.domain.transfer.event.TransferCompletedEvent;
import org.github.axon.tag.api.domain.transfer.event.TransferFailedEvent;
import org.github.axon.tag.api.domain.transfer.event.saga.TransferRequestedEvent;
import org.github.axon.tag.contract.entity.BankTransferEntry;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@ProcessingGroup("contract-processor")
public class BankTransferEntryMongoListener {

    @Autowired
    private BankTransferService service;

    @EventHandler
    public void on(TransactionInitiatedEvent ev) {
        log.info("View Handling {} event: {}", ev.getClass().getSimpleName(), ev);


        service.save(BankTransferEntry.builder()
                .transactionId(ev.getTransactionId())
                .amount(ev.getAmount())
                .sourceId(ev.getSourceAccountId())
                .destinationId(ev.getTargetAccountId())
                .status(BankTransferStatus.STARTED)
                .build()
        );

    }

    @EventHandler
    public void on(TransactionCancelledEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankTransferEntry bankTransfer = service.findById(event.getTransactionId());

        if (bankTransfer != null) {
            bankTransfer.setReason(event.getErrorCode());
            bankTransfer.setStatus(BankTransferStatus.FAILED);
            service.save(bankTransfer);
        } else {

            log.warn("Bank Transfer not found {}", event.getTransactionId());
        }


    }

    @EventHandler
    public void on(TransactionCompletedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankTransferEntry bankTransfer = service.findById(event.getTransactionId());

        if (bankTransfer != null) {
            bankTransfer.setStatus(BankTransferStatus.COMPLETED);
            service.save(bankTransfer);
        } else {

            log.warn("Bank Transfer not found {}", event.getTransactionId());
        }


    }

    /*@EventHandler
    public void on(BankAccountRemovedEvent event) {
        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);

        service.deleteById(event.getTransactionId());

    }*/
}
