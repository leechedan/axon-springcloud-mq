/*
package org.github.axon.tag.common.continuance.common;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.AbstractSnapshotEventEntry;
import org.axonframework.serialization.Serializer;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

*/
/**
 * 与domainEventEntry处于同一包，需要一同注册
 *//*

@Entity(name = "SnapshotEventEntry")
@Getter
@Setter
@Table(indexes = @Index(columnList = "aggregateIdentifier,sequenceNumber", unique = true))
public class SnapshotEventEntry extends AbstractSnapshotEventEntry<byte[]> {

    public SnapshotEventEntry(DomainEventMessage<?> eventMessage, Serializer serializer) {
        super(eventMessage, serializer, byte[].class);
    }

    protected SnapshotEventEntry() {
    }
}
*/
