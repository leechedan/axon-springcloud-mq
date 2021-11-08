<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPkgName}.${classInfo.className}Mapper">

    <sql id="BaseColumnList">
    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
        <#list classInfo.fieldList as fieldItem >
            ${classInfo.aliasTableName}.${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
        </#list>
    </#if>
    </sql>
    <select id="selectByRequest" resultType="${dtoPkgName}.${classInfo.className}DTO"
            parameterType="${searchRequestPkgName}.${classInfo.className}SearchRequest">
        SELECT <include refid="BaseColumnList" /> FROM ${classInfo.tableName} ${classInfo.aliasTableName}
        <trim prefix="where" prefixOverrides="and|or">
        <#list classInfo.fieldList as fieldItem >
        <if test="param.${fieldItem.fieldName} != null <#if fieldItem.fieldClass == "String"> and param.${fieldItem.fieldName} != ''</#if>">
            AND ${classInfo.aliasTableName}.${fieldItem.columnName} = ${r'#{param.'}${fieldItem.fieldName}}
        </if>
        </#list>
        ORDER BY ${classInfo.aliasTableName}.updated_time DESC
        </trim>
    </select>
</mapper>
