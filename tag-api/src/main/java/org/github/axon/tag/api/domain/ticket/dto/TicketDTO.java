package org.github.axon.tag.api.domain.ticket.dto;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.github.axon.tag.base.domain.common.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO 用于中途处理的 中间状态，
 * @desc Dto
 * @date 2021-06-30
 * @author lijc
*/
@Data
public class TicketDTO extends BaseDto implements Serializable {


    @ApiModelProperty(name = "id", value = "")
    private String id;

    @ApiModelProperty(name = "lockUser", value = "")
    private String lockUser;

    @ApiModelProperty(name = "name", value = "")
    private String name;

    @ApiModelProperty(name = "owner", value = "")
    private String owner;
}