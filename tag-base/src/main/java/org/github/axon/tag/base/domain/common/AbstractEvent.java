package org.github.axon.tag.base.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.serialization.Revision;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Revision("1.0.0")
public class AbstractEvent {

    @TargetAggregateIdentifier
    private Long id;
}
