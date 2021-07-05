package org.github.axon.tag.api.domain.customer.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author lijc
 * @date 2021-06-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRemovedEvent {


    @TargetAggregateIdentifier
    @ApiModelProperty(name = "id", value = "ID")
    private String id;



}
