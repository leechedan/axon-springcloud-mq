package org.github.axon.tag.api.domain.account.event;

import org.github.axon.tag.base.domain.common.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class BankAccountRemovedEvent implements AbstractEvent {

    private Long id;

    public BankAccountRemovedEvent(Long identifier) {
        this.id = id;
    }

}

