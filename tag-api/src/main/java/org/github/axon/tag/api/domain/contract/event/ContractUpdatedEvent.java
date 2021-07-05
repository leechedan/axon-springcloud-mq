package org.github.axon.tag.api.domain.contract.event;

import org.github.axon.tag.base.domain.common.AbstractEvent;
import org.github.axon.tag.api.domain.contract.service.ContractInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContractUpdatedEvent extends AbstractEvent implements ContractInterface {

    private String name;

    private String partyA;

    private String partyB;

    private String industryName;

    public ContractUpdatedEvent(Long  identifier, String name, String partyA, String partyB, String industryName) {
        super(identifier);
        this.name = name;
        this.partyA = partyA;
        this.partyB = partyB;
        this.industryName = industryName;
    }
}
