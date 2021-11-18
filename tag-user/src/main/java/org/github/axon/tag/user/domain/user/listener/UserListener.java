package org.github.axon.tag.user.domain.user.listener;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.github.axon.tag.api.domain.account.event.AccountCreatedEvent;
import org.github.axon.tag.api.domain.account.event.BalanceUpdatedEvent;
import org.github.axon.tag.api.domain.account.event.IUserEvent;
import org.github.axon.tag.api.domain.account.event.MoneyDepositedEvent;
import org.github.axon.tag.api.domain.account.event.MoneyWithdrawnEvent;
import org.github.axon.tag.user.entity.BankTransferRepository;
import org.github.axon.tag.user.entity.UserView;
import org.github.axon.tag.user.service.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import javax.validation.ValidationException;

@Component
@Slf4j
@ProcessingGroup("user-processor")
public class UserListener implements IUserEvent {

    @Autowired
    private UserViewService service;

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private EventProcessingConfiguration epc;

    @EventHandler
    @Override
    public void on(AccountCreatedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);

        UserView userView = new UserView(event.getAccountId(), event.getAccountHolderName(), event.getAmount());
        service.save(userView);
    }

    @EventHandler
    @Override
    public void on(BalanceUpdatedEvent event) {
        UserView userView = service.findById(event.getAccountId());

        if (userView != null) {
            userView.setAmount(event.getBalance());
            service.save(userView);
        } else {
            log.warn("Bank Transfer not found {}", event.getAccountId());
        }
    }

    @EventHandler
    @Override
    public void on(MoneyDepositedEvent event) {
        log.info("MoneyDepositedEvent {}", event);
        UserView userView = service.findById(event.getAccountId());
        if (userView != null) {
            userView.setAmount(userView.getAmount().add(event.getAmount()));
            service.save(userView);
        }
    }

    @EventHandler
    @Override
    public void on(MoneyWithdrawnEvent event) {
        log.info("MoneyWithdrawnEvent {}", event);
        UserView userView = service.findById(event.getAccountId());
        if (userView != null) {
            userView.setAmount(userView.getAmount().subtract(event.getAmount()));
            service.save(userView);
        } else {
            log.warn("cannot find user view {}", event.getAccountId());
        }
    }

    @SneakyThrows
    public void on() {

        Optional<TrackingEventProcessor> ret =
                epc.eventProcessor("user-processor", TrackingEventProcessor.class);

        if (ret.isPresent()) {

            service.replay();
            bankTransferRepository.deleteAll();

            TrackingEventProcessor proc = ret.get();

//            log.info("{}: supportsReset: {}", proc.getName(), proc.supportsReset());
//            log.info("{}: isRunning: {}", proc.getName(), proc.isRunning());
//            log.info("{}: processingStatus: {}", proc.getName(), proc.processingStatus().values());

            proc.shutDown();
            Thread.sleep(2000);
            proc.resetTokens();
            proc.start();
        } else {
            throw new ValidationException("Process not found.");
        }
    }
}
