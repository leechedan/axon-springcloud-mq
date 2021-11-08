package ${packageName}.${(moduleName)!}${(subPkgName)!};

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;
import ${basePkgName}.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @desc ${classInfo.classComment}
 * @author ${author} ${.now?string('yyyy-MM-dd')}
 */
@Data
public class ${classInfo.className} extends BaseEntity implements Serializable {

    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem ><#if !fieldItem.isEntityField>
    /**
     * ${fieldItem.columnName}  ${fieldItem.fieldComment}
     */
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};</#if>
    </#list></#if>
}