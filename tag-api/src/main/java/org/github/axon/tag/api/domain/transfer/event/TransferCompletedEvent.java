package org.github.axon.tag.api.domain.transfer.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.github.axon.tag.base.domain.common.AbstractEvent;

@Data
@NoArgsConstructor
public class TransferCompletedEvent extends AbstractEvent {

    public TransferCompletedEvent(Long id) {
        super(id);
    }
}
