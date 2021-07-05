package org.github.axon.tag.api.domain.contract.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateContractCommand extends UpdateContractCommand {

    private String name;

    private String partyA;

    private String partyB;

    private String industryName;

    public CreateContractCommand(Long identifier, String name, String partyA, String partyB, String industryName) {
        this.name = name;
        this.partyA = partyA;
        this.partyB = partyB;
        this.industryName = industryName;
    }
}
