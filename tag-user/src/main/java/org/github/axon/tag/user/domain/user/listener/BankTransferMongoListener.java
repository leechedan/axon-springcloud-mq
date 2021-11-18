package org.github.axon.tag.user.domain.user.listener;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.github.axon.tag.api.domain.common.enums.BankTransferStatus;
import org.github.axon.tag.api.domain.transfer.event.TransferCompletedEvent;
import org.github.axon.tag.api.domain.transfer.event.TransferFailedEvent;
import org.github.axon.tag.api.domain.transfer.event.saga.TransferRequestedEvent;
import org.github.axon.tag.user.entity.BankTransferEntry;
import org.github.axon.tag.user.service.BankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
//@ProcessingGroup("user-processor")
public class BankTransferMongoListener {

    @Autowired
    private BankTransferService service;

    @EventHandler
    public void on(TransferRequestedEvent ev) {
        log.info("View Handling {} event: {}", ev.getClass().getSimpleName(), ev);


        service.save(BankTransferEntry.builder()
                                      .transactionId(ev.getTransactionId())
                                      .amount(ev.getAmount())
                                      .sourceId(ev.getSourceId())
                                      .destinationId(ev.getDestinationId())
                                      .createDate(LocalDateTime.now())
                                      .status(BankTransferStatus.STARTED)
                                      .build()
        );
    }

    @EventHandler
    public void on(TransferCompletedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankTransferEntry bankTransfer = service.findById(event.getId());

        if (bankTransfer != null) {
            bankTransfer.setStatus(BankTransferStatus.COMPLETED);
            service.save(bankTransfer);
        } else {
            log.warn("Bank Transfer not found {}", event.getId());
        }
    }

    @EventHandler
    public void on(TransferFailedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankTransferEntry bankTransfer = service.findById(event.getTransactionId());

        if (bankTransfer != null) {
            bankTransfer.setStatus(BankTransferStatus.COMPLETED);
            service.save(bankTransfer);
        } else {

            log.warn("Bank Transfer not found {}", event.getTransactionId());
        }
    }

   /* @EventHandler
    public void on(BankAccountRemovedEvent event) {
        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);

        service.deleteById(event.getId());

    }*/
}
