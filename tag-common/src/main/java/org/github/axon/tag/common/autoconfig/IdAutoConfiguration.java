package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.github.axon.tag.common.config.UserPublisher;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.github.axon.tag.common.helper.WorkerIdService;
import org.github.axon.tag.common.repository.WorkerIdRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class IdAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UIDGenerator uidGenerator(WorkerIdService idService) {
        return new UIDGenerator(idService);
    }

    @Bean
    @ConditionalOnMissingBean
    public WorkerIdService idService(Registration registration, WorkerIdRepository workerIdRepository) {
        return new WorkerIdService(workerIdRepository, registration);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserPublisher userPublisher() {
        return new UserPublisher();
    }

    /*@Bean
    @ConditionalOnMissingBean
    public CustomDomainEventEntryListener customDomainEventEntryListener(
            CustomDomainEventEntryRepository repository, UserPublisher userPublisher) {
        log.debug("1 customDomainEventEntryListener");
        return new CustomDomainEventEntryListener(repository, userPublisher);
    }*/

}
