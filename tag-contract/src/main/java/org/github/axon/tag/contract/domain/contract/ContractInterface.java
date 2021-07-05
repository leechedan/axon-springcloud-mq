package org.github.axon.tag.contract.domain.contract;

import javax.validation.constraints.NotBlank;

public interface ContractInterface {
    @NotBlank
    String getName();

    @NotBlank
    String getPartyA();

    @NotBlank
    String getPartyB();

    @NotBlank
    String getIndustryName();
}
