package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.axonframework.extensions.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.extensions.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.axonframework.serialization.Serializer;
import org.github.axon.tag.common.continuance.common.CommandInterceptor;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Configuration
public class CommandRouterAutoConfiguration {

    @Bean
    @ConditionalOnMissingClass
    public CommandInterceptor commandInterceptor(UIDGenerator uidGenerator) {
        log.info("init commandInterceptor with id generator {}", uidGenerator);
        return new CommandInterceptor(uidGenerator);
    }
}
