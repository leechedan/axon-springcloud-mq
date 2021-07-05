package org.github.axon.tag.api.domain.customer.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;

/**
 * @author lijc
 * @date 2021-06-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRemoveCommand {

    @NotBlank
    @TargetAggregateIdentifier
    @ApiModelProperty(name = "id", value = "ID")
    private String id;



}
