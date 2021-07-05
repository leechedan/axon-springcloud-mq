package org.github.axon.tag.api.domain.account.event;

import org.github.axon.tag.base.domain.common.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class BankAccountRemovedEvent extends AbstractEvent {

    public BankAccountRemovedEvent(Long identifier) {
        super(identifier);
    }
}

