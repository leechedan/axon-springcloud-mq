package org.github.axon.tag.api.domain.contract.service;

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
