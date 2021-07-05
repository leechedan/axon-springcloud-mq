package org.github.axon.tag.api.domain.ticket.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author lijc
 * @date 2021-06-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRemovedEvent {


    @TargetAggregateIdentifier
    @ApiModelProperty(name = "id", value = "")
    private String id;



}
