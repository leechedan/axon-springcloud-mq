package ${packageName}.${(moduleName)!}${(subPkgName)!};

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ${basePkgName}.BasePageRequest;

/**
 * ${classInfo.classComment}接受前端请求的查询参数
 * @date ${date}
 * @author ${author}
*/
@Data
@ToString
@ApiModel(value = "${classInfo.className}SearchRequest", description = "${classInfo.classComment}查询参数")
public class ${className}SearchRequest extends BasePageRequest {

    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >

    @ApiModelProperty(name = "${fieldItem.fieldName}", value = "${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
    </#list>
    </#if>
}