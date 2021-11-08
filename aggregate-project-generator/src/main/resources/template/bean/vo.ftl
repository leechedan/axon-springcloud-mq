package ${packageName}.${(moduleName)!}${(subPkgName)!};

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * vo 返回给前端的数据
 * @desc ${classInfo.classComment}
 * @date ${date}
 * @author ${author}
 */
@Data
@ApiModel(value = "${className}Vo", description = "${classInfo.classComment}VO")
public class ${className}Vo implements Serializable {

    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >

    @ApiModelProperty(name = "${fieldItem.fieldName}", value = "${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
    </#list>
    </#if>
}