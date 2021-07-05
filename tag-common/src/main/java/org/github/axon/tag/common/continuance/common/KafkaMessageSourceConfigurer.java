package org.github.axon.tag.common.continuance.common;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.Component;
import org.axonframework.config.Configuration;
import org.axonframework.config.ModuleConfiguration;
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class KafkaMessageSourceConfigurer implements ModuleConfiguration {

    private Configuration configuration;

    private final List<Component<SubscribableKafkaMessageSource<?, ?>>> subscribableKafkaMessageSources = new ArrayList<>();

    @Override
    public void initialize(Configuration config) {
        log.info("init KafkaMessageSourceConfigurer");
        this.configuration = config;
    }

    @Override
    public int phase() {
        return Integer.MAX_VALUE;
    }

    public void registerSubscribableSource(
            Function<Configuration, SubscribableKafkaMessageSource<?, ?>> subscribableKafkaMessageSource) {
        subscribableKafkaMessageSources.add(new Component<>(
                () -> configuration, "subscribableKafkaMessageSource", subscribableKafkaMessageSource
        ));
    }

    @Override
    public void start() {
        subscribableKafkaMessageSources.stream().map(Component::get).forEach(SubscribableKafkaMessageSource::start);
    }

    @Override
    public void shutdown() {
        subscribableKafkaMessageSources.stream().map(Component::get).forEach(SubscribableKafkaMessageSource::close);
    }
}
