package org.github.axon.tag.api.domain.account.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBankAccountCommand{

    @TargetAggregateIdentifier
    private String id;
    private String name;

}