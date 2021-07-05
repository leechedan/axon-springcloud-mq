package org.github.axon.tag.common.autoconfig;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableRabbit
@AutoConfigureAfter(AxonAutoConfiguration.class)
@ConditionalOnProperty(prefix = "axon.amqp", name = "enable", havingValue = "true", matchIfMissing = false)
public class RabbitAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "axon.amqp.source", name = "enable", havingValue = "true", matchIfMissing = false)
    public SpringAMQPMessageSource menuMessageSource(Serializer messageConverter) {
        log.info("init menuMessageSource");
        return new SpringAMQPMessageSource(messageConverter) {
            @Override
            @RabbitListener(queues = "${axon.queue}")
            public void onMessage(Message message, Channel channel) {
                log.info("{}", message);
                super.onMessage(message, channel);
            }
        };
    }

    @Bean
    public FanoutExchange exchange(@Value("${axon.amqp.exchange}") String name) {
        log.info("init exchange {}", name);
        return new FanoutExchange(name);
    }

    @Bean
    public Queue queue(@Value("${axon.queue}") String name) {
        log.info("init queue {}", name);
        Queue queue = new Queue(name);
        return queue;
    }

    @Bean
    public Binding binding(FanoutExchange exchange, Queue queue) {
        log.info("init binding exchange:{} queue:{}", exchange.getName(), queue.getName());
        return BindingBuilder.bind(queue).to(exchange);
    }
}