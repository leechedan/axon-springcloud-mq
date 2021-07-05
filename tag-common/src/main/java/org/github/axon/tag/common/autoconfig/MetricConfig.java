package org.github.axon.tag.common.autoconfig;

import io.micrometer.core.instrument.MeterRegistry;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.MessageMonitorFactory;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.micrometer.CapacityMonitor;
import org.axonframework.micrometer.MessageCountingMonitor;
import org.axonframework.micrometer.MessageTimerMonitor;
import org.axonframework.micrometer.PayloadTypeMessageMonitorWrapper;
import org.axonframework.monitoring.MultiMessageMonitor;
import org.axonframework.springboot.MetricsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {

	@Bean
	@ConfigurationProperties(prefix = "metrics")
	public MetricsProperties metricsProperties() {
		return new MetricsProperties();
	}
/*
	@PostConstruct
	public void schedule(MetricsProperties properties) {
		final InfluxReporter reporter = influxReporter();
		reporter.start(properties.getScheduleSeconds(), TimeUnit.SECONDS);
	}*/

	@Bean
	public ConfigurerModule metricConfigurer(MeterRegistry meterRegistry) {
		return configurer -> {
			instrumentEventProcessors(meterRegistry, configurer);
			instrumentCommandBus(meterRegistry, configurer);
		};
	}

	private void instrumentEventProcessors(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			// We want to count the messages per type of event being published.
			PayloadTypeMessageMonitorWrapper<MessageCountingMonitor> messageCounterPerType = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> MessageCountingMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());
			// And we also want to set a message timer per payload type
			PayloadTypeMessageMonitorWrapper<MessageTimerMonitor> messageTimerPerType = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> MessageTimerMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());
			// Which we group in a MultiMessageMonitor
			return new MultiMessageMonitor<>(messageCounterPerType, messageTimerPerType);
		};
		configurer.configureMessageMonitor(TrackingEventProcessor.class, messageMonitorFactory);
	}

	private void instrumentCommandBus(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			PayloadTypeMessageMonitorWrapper<MessageCountingMonitor> messageCounterPerType = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> MessageCountingMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());

			PayloadTypeMessageMonitorWrapper<MessageTimerMonitor> messageTimerPerType = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> MessageTimerMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());

			PayloadTypeMessageMonitorWrapper<CapacityMonitor> capacityMonitor = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> CapacityMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());

			return new MultiMessageMonitor<>(messageCounterPerType, messageTimerPerType, capacityMonitor);
		};
		configurer.configureMessageMonitor(CommandBus.class, messageMonitorFactory);
	}
}