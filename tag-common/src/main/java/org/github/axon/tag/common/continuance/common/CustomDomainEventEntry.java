package org.github.axon.tag.common.continuance.common;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.eventhandling.AbstractSequencedDomainEventEntry;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.serialization.Serializer;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "domainevents")
@Getter
@Setter
//@Table(indexes = @Index(columnList = "aggregateIdentifier,sequenceNumber", unique = true))
//@EntityListeners(CustomDomainEventEntryListener.class)
public class CustomDomainEventEntry extends AbstractSequencedDomainEventEntry<byte[]> {

//    @Column(columnDefinition = "tinyint(1) default 0")
    @Field("sent")
    private boolean sent = false;

    public CustomDomainEventEntry(DomainEventMessage<?> eventMessage, Serializer serializer) {
        super(eventMessage, serializer, byte[].class);
        this.setSent(false);
    }

    /**
     * Default constructor required by JPA
     */
    protected CustomDomainEventEntry() {
    }
}
