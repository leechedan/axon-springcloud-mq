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

    //    @Bean
//    @ConditionalOnMissingClass
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient, Registration localServiceInstance) {
        log.info("init springCloudCommandRouter {}", localServiceInstance);
        log.info("{} {}", discoveryClient.description(), discoveryClient.getServices());
        log.info("{} {} {} {} {} {}", localServiceInstance.getHost(), localServiceInstance.getPort(),
                 localServiceInstance.getServiceId(), localServiceInstance.getMetadata(),
                 localServiceInstance.getScheme(), localServiceInstance.getUri());
        return SpringCloudCommandRouter.builder()
                                       .discoveryClient(discoveryClient)
                                       .routingStrategy(new AnnotationRoutingStrategy())
                                       .localServiceInstance(localServiceInstance)
                                       .build();
    }

    //@Bean
    // @ConditionalOnMissingClass
    public CommandBusConnector springHttpCommandBusConnector(
            @Qualifier("localSegment") CommandBus localSegment,
            RestOperations restOperations,
            Serializer serializer) {
        log.info("init springHttpCommandBusConnector");
        return SpringHttpCommandBusConnector.builder()
                                            .localCommandBus(localSegment)
                                            .restOperations(restOperations)
                                            .serializer(serializer)
                                            .build();
    }

    // @Primary
    // @Bean
    @ConditionalOnMissingClass
    public DistributedCommandBus springCloudDistributedCommandBus(
            CommandRouter commandRouter,
            CommandBusConnector commandBusConnector) {
        log.info("init DistributedCommandBus");
        return DistributedCommandBus.builder()
                                    .commandRouter(commandRouter)
                                    .connector(commandBusConnector)
                                    .build();
    }


    // @Bean
    // @ConditionalOnMissingClass
    public CommandGateway commandGateway(DistributedCommandBus distributedCommandBus
            , CommandInterceptor commandInterceptor) {
        log.info("init commandGateway with commandInterceptor {}", commandInterceptor);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        RetryScheduler rs = IntervalRetryScheduler.builder().retryExecutor(scheduledExecutorService)
                                                  .maxRetryCount(1).retryInterval(2000).build();
        return DefaultCommandGateway.builder()
                                    .dispatchInterceptors(commandInterceptor)
                                    .commandBus(distributedCommandBus).retryScheduler(rs).build();
    }

    @Bean
    @ConditionalOnMissingClass
    public CommandInterceptor commandInterceptor(UIDGenerator uidGenerator) {
        log.info("init commandInterceptor with id generator {}", uidGenerator);
        return new CommandInterceptor(uidGenerator);
    }
}
