package org.github.axon.tag.api.domain.contract.command;

import org.github.axon.tag.base.domain.common.AbstractCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeleteContractCommand implements AbstractCommand {

    private Long id;
}
