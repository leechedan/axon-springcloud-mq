package org.github.axon.tag.api.domain.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.github.axon.tag.base.domain.common.AbstractCommand;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountCommand extends AbstractCommand {

    private String accountHolderName;
}
