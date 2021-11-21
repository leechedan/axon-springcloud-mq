package org.github.axon.tag.api.domain.contract.command;

import org.github.axon.tag.base.domain.common.AbstractCommand;
import org.github.axon.tag.api.domain.contract.service.ContractInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateContractCommand implements AbstractCommand, ContractInterface {

    private Long id;

    private String name;

    private String partyA;

    private String partyB;

    private String industryName;

    public UpdateContractCommand(Long identifier, String name, String partyA, String partyB, String industryName) {
        this.id = identifier;
        this.name = name;
        this.partyA = partyA;
        this.partyB = partyB;
        this.industryName = industryName;
    }
}
