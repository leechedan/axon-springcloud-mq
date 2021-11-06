package org.github.axon.tag.base.domain.common;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;

import java.io.Serializable;

/**
 * @author lee
 * @date 2020/2/24 12:50
 */
@Data
@FieldNameConstants
public class BaseAggregate implements Serializable {

    @AggregateIdentifier
    @RoutingKey
    Long id;
    private String adminId;
    private String userId;

    protected void applyMetaData(MetaData metaData) {
    }
}
