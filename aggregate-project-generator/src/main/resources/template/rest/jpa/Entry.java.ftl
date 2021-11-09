package ${packageName}.${(moduleName)!}${(subPkgName)!};

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ${(basePkgName)!}.BaseEntry;
/**
 * @author ${author}
 * @date ${date}
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "${classInfo.tableName}")
public class ${aggregate?cap_first}Entry extends BaseEntry {

    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem ><#if fieldItem.isPrimaryKey>@Id</#if>
    @ApiModelProperty(name = "${fieldItem.fieldName}", value = "${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

    </#list></#if>
}
