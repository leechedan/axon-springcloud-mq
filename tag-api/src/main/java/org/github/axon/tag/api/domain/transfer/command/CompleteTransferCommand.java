package org.github.axon.tag.api.domain.transfer.command;

import org.github.axon.tag.base.domain.common.AbstractCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@ToString
public class CompleteTransferCommand implements AbstractCommand {

    @TargetAggregateIdentifier
    private Long id;

}
