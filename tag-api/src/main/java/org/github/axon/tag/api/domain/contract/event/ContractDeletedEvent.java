package org.github.axon.tag.api.domain.contract.event;

import org.github.axon.tag.base.domain.common.AbstractEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContractDeletedEvent implements AbstractEvent {

    private Long id;

}
