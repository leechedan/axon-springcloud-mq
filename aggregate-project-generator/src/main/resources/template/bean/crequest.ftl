package ${packageName}.${(moduleName)!}${(subPkgName)!};

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${basePkgName}.BaseRequest;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

/**
 * ${classInfo.classComment}创建和更新请求参数
 * @date ${date}
 * @author ${author}
 */
@Data
@ToString
@ApiModel(value = "${className}CreateRequest", description = "${classDesc}创建请求参数")
public class ${className}CreateRequest extends BaseRequest implements Serializable {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
 <#list classInfo.fieldList as fieldItem >
  <#if !fieldItem.ignore || fieldItem.isPrimaryKey>

    @ApiModelProperty(name = "${fieldItem.fieldName}", value = "${fieldItem.fieldComment}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
 </#if>
 </#list>
</#if>
}