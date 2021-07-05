package org.github.axon.tag.base.domain.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractCommand {

    @TargetAggregateIdentifier
    @RoutingKey
    @ApiModelProperty(name = "id", value = "")
    private Long identifier;
}
