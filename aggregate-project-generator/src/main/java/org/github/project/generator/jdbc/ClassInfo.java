package org.github.project.generator.jdbc;

import lombok.Data;

import java.util.List;

/**
 * ******************************
 * author：
 * createTime:   2020/1/9 17:08
 * description:  ClassInfo 表信息
 * version:      V1.0
 * ******************************
 */
@Data
public class ClassInfo {

    /***
     * DB表名
     */
    private String tableName;

    /**
     * class类名  一般大写前缀
     */
    private String className;

    /***
     * 大写的表名别名
     */
    private String aliasTableName;

    /**
     * class实体参数名 如: classInfo
     */
    private String modelName;

    /**
     * class注释
     */
    private String classComment;

    /***
     * 主键字段及值 (默认以持有字段的 index=0 为主键)
     */
    private FieldInfo key;

    /**
     * 持有字段
     */
    private List<FieldInfo> fieldList;

    /***
     * dto包名
     */
    private String dtoPkgName;

    /***
     * entity包名
     */
    private String entityPkgName;

    /***
     * mapper包名
     */
    private String mapperPkgName;

    /***
     * service包名
     */
    private String servicePkgName;


    /***
     * serviceImpl包名
     */
    private String serviceImplPkgName;

    /**
     * path路径名
     */
    private String pathName;
}