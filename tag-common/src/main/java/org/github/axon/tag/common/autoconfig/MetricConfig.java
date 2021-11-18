package org.github.axon.tag.common.autoconfig;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.MessageMonitorFactory;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.micrometer.CapacityMonitor;
import org.axonframework.micrometer.MessageCountingMonitor;
import org.axonframework.micrometer.MessageTimerMonitor;
import org.axonframework.micrometer.TagsUtil;
import org.axonframework.monitoring.MultiMessageMonitor;
import org.axonframework.queryhandling.QueryBus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Slf4j
@Configuration
public class MetricConfig {

	@Bean
//	@ConditionalOnMissingBean
	ConfigurerModule metricConfigurer(MeterRegistry meterRegistry) {
		return configurer -> {
			instrumentEventStore(meterRegistry, configurer);
			instrumentEventProcessors(meterRegistry, configurer);
			instrumentCommandBus(meterRegistry, configurer);
			instrumentQueryBus(meterRegistry, configurer);
		};
	}

	private void instrumentEventStore(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			log.info("eventStore type:{} name:{} ", componentType, componentName);
			MessageCountingMonitor messageCounter = MessageCountingMonitor.buildMonitor(
					"EventStoreCount", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			MessageTimerMonitor messageTimer = MessageTimerMonitor.buildMonitor(
					"EventStoreTimer", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			return new MultiMessageMonitor<>(messageCounter, messageTimer);
		};
		configurer.configureMessageMonitor(EventStore.class, messageMonitorFactory);
	}

	private void instrumentEventProcessors(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			log.info("eventProcessor type:{} name:{} ", componentType, componentName);
			MessageCountingMonitor messageCounter = MessageCountingMonitor.buildMonitor(
					"eventProcessorCount", meterRegistry,
					message -> Tags.of(
							TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
							TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			MessageTimerMonitor messageTimer = MessageTimerMonitor.buildMonitor(
					"eventProcessorTimer", meterRegistry,
					message -> Tags.of(
							TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
							TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			CapacityMonitor capacityMonitor1Minute = CapacityMonitor.buildMonitor(
					"eventProcessorCapacity", meterRegistry,
					message -> Tags.of(
							TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
							TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);

			return new MultiMessageMonitor<>(messageCounter, messageTimer, capacityMonitor1Minute);
		};
		configurer.configureMessageMonitor(TrackingEventProcessor.class, messageMonitorFactory);
	}

	private void instrumentCommandBus(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			log.info("command_bus type:{} name:{} ", componentType, componentName);
			MessageCountingMonitor messageCounter = MessageCountingMonitor.buildMonitor(
					"CommandBusCount", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			MessageTimerMonitor messageTimer = MessageTimerMonitor.buildMonitor(
					"CommandBusTimer", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);

			CapacityMonitor capacityMonitor1Minute = CapacityMonitor.buildMonitor(
					"CommandBusCapacity", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);

			return new MultiMessageMonitor<>(messageCounter, messageTimer, capacityMonitor1Minute);
		};
		configurer.configureMessageMonitor(CommandBus.class, messageMonitorFactory);
	}

	private void instrumentQueryBus(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			log.info("query bus type:{} name:{} ", componentType, componentName);
			MessageCountingMonitor messageCounter = MessageCountingMonitor.buildMonitor(
					"QueryBusCount", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			MessageTimerMonitor messageTimer = MessageTimerMonitor.buildMonitor(
					"QueryBusTimer", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);
			CapacityMonitor capacityMonitor1Minute = CapacityMonitor.buildMonitor(
					"QueryBusCapacity", meterRegistry,
					message -> Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.getPayloadType().getSimpleName(),
									   TagsUtil.PROCESSOR_NAME_TAG, componentName)
							.and(message.getMetaData().entrySet().stream().filter(i->!i.getKey().contains("Id"))
									.map(s -> Tag.of(s.getKey(), s.getValue().toString()))
									.collect(Collectors.toList()))
			);

			return new MultiMessageMonitor<>(messageCounter, messageTimer, capacityMonitor1Minute);
		};
		configurer.configureMessageMonitor(QueryBus.class, messageMonitorFactory);
	}
}
