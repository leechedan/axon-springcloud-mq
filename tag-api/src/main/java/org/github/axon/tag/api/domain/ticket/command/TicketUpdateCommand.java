package org.github.axon.tag.api.domain.ticket.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;

/**
 * @author lijc
 * @date 2021-06-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketUpdateCommand {

    @TargetAggregateIdentifier
    @ApiModelProperty(name = "id", value = "")
    private String id;
    
    @ApiModelProperty(name = "lockUser", value = "")
    private String lockUser;
    
    @ApiModelProperty(name = "name", value = "")
    private String name;
    
    @ApiModelProperty(name = "owner", value = "")
    private String owner;
}
