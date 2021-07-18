package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.github.axon.tag.common.config.UserPublisher;
import org.github.axon.tag.common.continuance.common.CustomDomainEventEntryListener;
import org.github.axon.tag.common.continuance.common.CustomerEventUpCaster;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.github.axon.tag.common.helper.WorkerIdService;
import org.github.axon.tag.common.repository.CustomDomainEventEntryRepository;
import org.github.axon.tag.common.repository.WorkerIdRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EntityScan(basePackages = {"org.github.axon.tag.common.helper"})
public class IdAutoConfiguration {

    @Bean
    @ConditionalOnMissingClass
    public UIDGenerator uidGenerator(WorkerIdService idService) {
        return new UIDGenerator(idService);
    }

    @Bean
    @ConditionalOnMissingBean
    public WorkerIdService idService(Registration registration, WorkerIdRepository workerIdRepository) {
        return new WorkerIdService(workerIdRepository, registration);
    }

    @Bean
    @ConditionalOnMissingClass
    public UserPublisher userPublisher() {
        return new UserPublisher();
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomDomainEventEntryListener customDomainEventEntryListener(
            CustomDomainEventEntryRepository repository, UserPublisher userPublisher) {
        log.info("1 customDomainEventEntryListener");
        return new CustomDomainEventEntryListener(repository, userPublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public SingleEventUpcaster singleEventUpcaster() {
        return new CustomerEventUpCaster();
    }
}
