package org.github.axon.tag.user.domain.user.query.entry;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.github.axon.tag.base.domain.common.BaseEntry;
/**
 * @author lijc
 * @date 2021-06-30
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "tb_ticket")
public class TicketEntry extends BaseEntry {

    @ApiModelProperty(name = "id", value = "")
    @Id
    private String id;
    @ApiModelProperty(name = "lockUser", value = "")
    
    private String lockUser;
    @ApiModelProperty(name = "name", value = "")
    
    private String name;
    @ApiModelProperty(name = "owner", value = "")
    
    private String owner;
}
