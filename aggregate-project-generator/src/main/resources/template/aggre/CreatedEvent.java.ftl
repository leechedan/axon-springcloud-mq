package ${packageName}.${(moduleName)!}${(subPkgName)!};

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import ${(basePkgName)!}.AbstractEvent;
/**
 * @author ${author}
 * @date ${date}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${aggregate?cap_first}CreatedEvent extends AbstractEvent {

    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem ><#if !fieldItem.isPrimaryKey>

    @ApiModelProperty(name = "${fieldItem.fieldName}", value = "${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
    </#if></#list></#if>
}
