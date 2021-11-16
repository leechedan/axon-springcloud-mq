package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.github.axon.tag.common.continuance.common.CommandInterceptor;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Configuration
@AutoConfigureBefore(AxonAutoConfiguration.class)
public class CommandRouterAutoConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public CommandGateway commandGateway(@Autowired(required = false) CommandBus distributedCommandBus
            , CommandInterceptor commandInterceptor) {
        log.debug("init commandGateway with commandInterceptor {}", commandInterceptor);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        RetryScheduler rs = IntervalRetryScheduler.builder().retryExecutor(scheduledExecutorService)
                                                  .maxRetryCount(1).retryInterval(2000).build();
        return DefaultCommandGateway.builder()
                                    .dispatchInterceptors(commandInterceptor)
                                    .commandBus(distributedCommandBus).retryScheduler(rs).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandInterceptor commandInterceptor(UIDGenerator uidGenerator) {
        log.debug("init commandInterceptor with id generator {}", uidGenerator);
        return new CommandInterceptor(uidGenerator);
    }
}
