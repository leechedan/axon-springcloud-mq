package org.github.axon.tag.common.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableRabbit
@AutoConfigureAfter(AxonAutoConfiguration.class)
@ConditionalOnClass(name = {"org.springframework.amqp.core.Queue"})
public class RabbitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FanoutExchange exchange(@Value("${axon.amqp.exchange}") String name) {
        log.debug("init exchange {}", name);
        return new FanoutExchange(name);
    }

    @Bean
    @ConditionalOnMissingBean
    public Queue queue(@Value("${axon.queue}") String name) {
        log.debug("init queue {}", name);
        Queue queue = new Queue(name);
        return queue;
    }

    @Bean
    @ConditionalOnMissingBean
    public Binding binding(FanoutExchange exchange, Queue queue) {
        log.debug("init binding exchange:{} queue:{}", exchange.getName(), queue.getName());
        return BindingBuilder.bind(queue).to(exchange);
    }
}