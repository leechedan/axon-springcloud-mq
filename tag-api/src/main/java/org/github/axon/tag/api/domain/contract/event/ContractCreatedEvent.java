package org.github.axon.tag.api.domain.contract.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContractCreatedEvent extends ContractUpdatedEvent {

    public ContractCreatedEvent(Long  identifier, String name, String partyA, String partyB, String industryName) {
        super(identifier, name, partyA, partyB, industryName);
    }
}
