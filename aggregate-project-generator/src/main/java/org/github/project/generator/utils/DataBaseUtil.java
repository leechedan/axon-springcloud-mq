package org.github.project.generator.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Tuple;
import org.github.project.generator.jdbc.ClassInfo;
import org.github.project.generator.jdbc.ConfigurationInfo;
import org.github.project.generator.jdbc.FieldInfo;
import org.github.project.generator.jdbc.GlobleConfig;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ******************************
 * author：
 * createTime:   2020/1/9 18:03
 * description:  库解析工具类
 *               解析表和字段
 * version:      V1.0
 * ******************************
 */
public class DataBaseUtil {

    public final static String UNDERLINE = "_";
    /***
     * 根据指定库获取单表相关参数
     * @param tableName   表名
     */
    public static ClassInfo parseClassInfo(String tableName, String tableComment, List<String> skipFieldNames
            , List<String> entityFieldNames) throws SQLException {

        ConfigurationInfo config = GlobleConfig.getGlobleConfig();
        // tableSql
        String tableInfoSql = getTableInfoSql(tableName);

        Statement statement = DBUtil.getConnection().createStatement();

        ResultSet tableResult = statement.executeQuery(tableInfoSql);

        // 构建ClassInfo信息
        ClassInfo classInfo = new ClassInfo();
        classInfo.setTableName(tableName);
        String tableNameWithoutPrefix = tableName;
        if (StringUtils.isNoneBlank(config.getSkipTablePrefix()) && tableName.startsWith(config.getSkipTablePrefix())){
            tableNameWithoutPrefix = tableName.substring(config.getSkipTablePrefix().length());
        }
        classInfo.setPathName(StringUtil.getPathStr(tableNameWithoutPrefix));

        // className信息
        String className = StringUtil.upperCaseFirst(StringUtil.underlineToCamelCase(tableNameWithoutPrefix));
        classInfo.setClassName(className);
        classInfo.setModelName(StringUtil.lowerCaseFirst(className));
        classInfo.setClassComment(tableComment);
        classInfo.setAliasTableName(StringUtil.upperFstChar(tableName));

        List<FieldInfo> fieldList = new ArrayList<>();
        // 1 column_name, 2 data_type 3 column_comment
        while (tableResult.next()) {
            FieldInfo fieldInfo = new FieldInfo();
            if (skipFieldNames.contains(tableResult.getString(1))){
                fieldInfo.setIgnore(true);
            }
            if ("id".equals(tableResult.getString(1))){
                fieldInfo.setIsPrimaryKey(true);
            } else {
                fieldInfo.setIsPrimaryKey(false);
            }
            if (entityFieldNames.contains(tableResult.getString(1))){
                fieldInfo.setIsEntityField(true);
            }
            fieldInfo.setColumnName(tableResult.getString(1));
            fieldInfo.setFieldClass(typeMapping.get(tableResult.getString(2)));

            String fieldName = StringUtil.underlineToCamelCase(tableResult.getString(1));
            fieldInfo.setFieldName(fieldName);
            fieldInfo.setFieldComment(tableResult.getString(3));

            // 维护表结构字段 2 data_type,4 6 length, 7 nullAble
            fieldInfo.setDataType(tableResult.getString(2));
            fieldInfo.setMethodField(StringUtil.underlineToHump(tableResult.getString(1)));
            fieldInfo.setMaxLength(StringUtils.isNotBlank(tableResult.getString(4)) ? tableResult.getString(4) : tableResult.getString(6));
            fieldInfo.setNullable(tableResult.getString(7));
            fieldList.add(fieldInfo);
        }

        classInfo.setFieldList(fieldList);

        // 设置主键字段
        if (CollectionUtil.isEmpty(fieldList)) {
            classInfo.setKey(new FieldInfo());
        } else if ("Long".equalsIgnoreCase(fieldList.get(0).getFieldClass())) {
            classInfo.setKey(fieldList.get(0));
        } else {
            return null;
        }

        tableResult.close();
        statement.close();
        return classInfo;
    }

    /***
     * 根据指定库获取所有表名
     */
    public static List<Tuple> getAllTableNames () throws SQLException {
        // result
        List<Tuple> result = new ArrayList<>();

        // sql
        String sql = getTables();

        Statement statement = DBUtil.getConnection().createStatement();

        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            result.add(new Tuple(rs.getString(1), rs.getString(2)));
        }
        return result;
    }

    /***
     * TableInfo SQL
     * @param tableName tableName
     */
    private static String getTableInfoSql(String tableName) {
        return MessageFormat.format("select column_name,data_type,column_comment,numeric_precision," +
                "numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns " +
                "where table_name = \"{0}\" and table_schema = \"{1}\"", tableName, GlobleConfig.getGlobleConfig().getDataBase());
    }

    /***
     * 获取所有Tables SQL
     */
    private static String getTables() {
        return MessageFormat.format("select table_name,table_comment from information_schema.tables where table_schema=\"{0}\" and table_type=\"{1}\";",
                GlobleConfig.getGlobleConfig().getDataBase(), "base table");
    }

    private static Map<String, String> typeMapping = new HashMap<>();

    static {
        typeMapping.put("int"       , "Integer");
        typeMapping.put("char"      , "String");
        typeMapping.put("varchar"   , "String");
        typeMapping.put("datetime"  , "LocalDateTime");
        typeMapping.put("timestamp" , "LocalDateTime");
        typeMapping.put("bit"       , "Integer");
        typeMapping.put("tinyint"   , "Integer");
        typeMapping.put("smallint"  , "Integer");
        typeMapping.put("year"      , "LocalDate");
        typeMapping.put("date"      , "LocalDate");
        typeMapping.put("bigint"    , "Long");
        typeMapping.put("decimal"   , "BigDecimal");
        typeMapping.put("double"    , "Double");
        typeMapping.put("float"     , "Float");
        typeMapping.put("numeric"   , "Integer");
        typeMapping.put("text"      , "String");
        typeMapping.put("tinytext", "String");
        typeMapping.put("mediumtext", "String");
        typeMapping.put("longtext"  , "String");
        typeMapping.put("time"      , "LocalDateTime");
        typeMapping.put("json"      , "Object");
    }
}
