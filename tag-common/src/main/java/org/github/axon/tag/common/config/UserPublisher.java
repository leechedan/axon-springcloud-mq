package org.github.axon.tag.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.axon.tag.common.continuance.common.CustomDomainEventEntry;
import org.github.axon.tag.common.continuance.common.DomainEvent;

import java.text.MessageFormat;
import java.util.HashMap;

@AllArgsConstructor
@Slf4j
public class UserPublisher {

    public void sendEvent(DomainEvent event) {

        log.info(MessageFormat.format("prepare to send message : {0}]", event));
    }

    public void sendEvent(CustomDomainEventEntry event) {
        log.info("send Event:{} fake", event);
        // use stream to send message here
        ObjectMapper mapper = new ObjectMapper();

        HashMap payload = null;
        HashMap metaData = null;
        try {
            payload = mapper.readValue(event.getPayload().getData(), HashMap.class);
            metaData = mapper.readValue(event.getMetaData().getData(), HashMap.class);
        } catch (Exception exception) {
            log.error(MessageFormat.format("byte[] to string failed; exception: {0}", exception));
        }

        if (payload == null || metaData == null) {
            log.warn(MessageFormat.format("nothing to send; exception: {0}", event.getEventIdentifier()));
            return;
        }

        DomainEvent<HashMap, HashMap> domainEvent = new DomainEvent<>(
                event.getType(),
                event.getAggregateIdentifier(),
                event.getPayload().getType().getName(),
                event.getPayload().getType().getRevision(),
                event.getSequenceNumber(),
                event.getEventIdentifier(),
                event.getTimestamp(),
                payload,
                metaData);

        this.sendEvent(domainEvent);
    }
}
