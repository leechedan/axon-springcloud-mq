package org.github.axon.tag.common.continuance.common;

import org.github.axon.tag.common.config.UserPublisher;
import org.github.axon.tag.common.repository.CustomDomainEventEntryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.PostPersist;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Slf4j
public class CustomDomainEventEntryListener {

    private CustomDomainEventEntryRepository customDomainEventEntryRepository;

    private UserPublisher userPublisher;

    @Autowired
    public void init(CustomDomainEventEntryRepository customDomainEventEntryRepository, UserPublisher userPublisher) {
        this.customDomainEventEntryRepository = customDomainEventEntryRepository;
        this.userPublisher = userPublisher;
    }

    @PostPersist
    void onPersist(CustomDomainEventEntry entry) {

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {

            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_COMMITTED) {
                    CompletableFuture.runAsync(() -> sendEvent(entry.getEventIdentifier()));
                }
            }
        });
    }

    @Transactional
    public void sendEvent(String identifier) {
        CustomDomainEventEntry eventEntry = customDomainEventEntryRepository.findByEventIdentifier(
                identifier);

        if (!eventEntry.isSent()) {
            log.info("send identifier:{} success");
            userPublisher.sendEvent(eventEntry);
            eventEntry.setSent(true);
            customDomainEventEntryRepository.save(eventEntry);
        }
    }
}
