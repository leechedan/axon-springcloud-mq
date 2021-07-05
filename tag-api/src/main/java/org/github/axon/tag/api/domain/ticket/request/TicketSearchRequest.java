package org.github.axon.tag.api.domain.ticket.request;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

import org.github.axon.tag.base.domain.common.BasePageRequest;

/**
 * 接受前端请求的查询参数
 * @date 2021-06-30
 * @author lijc
*/
@Data
@ToString
@ApiModel(value = "TicketSearchRequest", description = "查询参数")
public class TicketSearchRequest extends BasePageRequest {


    @ApiModelProperty(name = "id", value = "")
    private String id;

    @ApiModelProperty(name = "lockUser", value = "")
    private String lockUser;

    @ApiModelProperty(name = "name", value = "")
    private String name;

    @ApiModelProperty(name = "owner", value = "")
    private String owner;
}