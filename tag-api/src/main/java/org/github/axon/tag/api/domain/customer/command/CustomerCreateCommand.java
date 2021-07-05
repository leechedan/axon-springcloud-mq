package org.github.axon.tag.api.domain.customer.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author lijc
 * @date 2021-06-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateCommand {

    @TargetAggregateIdentifier
    @ApiModelProperty(name = "id", value = "ID")
    private String id;
    
    @ApiModelProperty(name = "deposit", value = "余额")
    private Double deposit;
    
    @ApiModelProperty(name = "password", value = "密码")
    private String password;
    
    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;
}
