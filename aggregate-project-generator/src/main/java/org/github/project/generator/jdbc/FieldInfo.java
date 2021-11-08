package org.github.project.generator.jdbc;

import lombok.Data;

/**
 * ******************************
 * author：
 * createTime:   2020/1/9 17:08
 * description:  FieldInfo 字段信息
 * version:      V1.0
 * ******************************
 */
@Data
public class FieldInfo {

    /**
     * 字段名
     */
    private String columnName;

    /**
     * TenantId 类似首字母大写
     */
    private String methodField;

    /**
     * Model名
     */
    private String fieldName;

    /**
     * 所属java类 String Date Integer Long
     */
    private String fieldClass;

    /**
     * 字段注释
     */
    private String fieldComment;

    // ***************************** 以下内容作为表结构文件映射内容

    private String  dataType;

    private String  maxLength;

    private String  nullable;

    private Boolean ignore = false;

    private Boolean isPrimaryKey = false;

    private Boolean isEntityField = false;
}
