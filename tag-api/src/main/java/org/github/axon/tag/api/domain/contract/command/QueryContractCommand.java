package org.github.axon.tag.api.domain.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueryContractCommand {
    @NotBlank
    @NotNull
    private String id;

    private Instant endDate;
}
