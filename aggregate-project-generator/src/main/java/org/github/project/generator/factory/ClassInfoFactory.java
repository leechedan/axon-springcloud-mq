package org.github.project.generator.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Tuple;
import org.apache.commons.lang3.RegExUtils;
import org.github.project.generator.jdbc.ClassInfo;
import org.github.project.generator.jdbc.ConfigurationInfo;
import org.github.project.generator.jdbc.GlobleConfig;
import org.github.project.generator.utils.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ******************************
 * author：
 * createTime:   2020/1/9 18:24
 * description:  ClassInfoFactory
 * version:      V1.0
 * ******************************
 */
@Slf4j
public class ClassInfoFactory {

    private volatile static List<ClassInfo> CLASS_INFO_LIST = new ArrayList<>();

    public static List<ClassInfo> getClassInfoList() {
        if (CollectionUtil.isEmpty(CLASS_INFO_LIST)) {
            synchronized (ClassInfoFactory.class) {
                if (CollectionUtil.isEmpty(CLASS_INFO_LIST)) {
                    try {
                        // 获取配置项
                        ConfigurationInfo config = GlobleConfig.getGlobleConfig();
                        List<String> skipFieldNames = Arrays.asList(
                                StringUtils.split(config.getSkipFields(), ","));
                        List<String> entityFieldNames = Arrays.asList(
                                StringUtils.split(config.getEntityFields(), ","));
                        List<Tuple> tableNames = DataBaseUtil.getAllTableNames();
                        for (Tuple tuple : tableNames) {
                            // 仅加载 *; 配置项 或者 include包含项才进行处理
                            if("*".equals(config.getInclude()) || ((String)tuple.get(0)).startsWith(config.getInclude()) ||
                                    config.getIncludeMap().containsKey(tuple.get(0))
                            ) {
                                ClassInfo classInfo = DataBaseUtil.parseClassInfo(tuple.get(0), tuple.get(1), skipFieldNames, entityFieldNames);
                                if (null == classInfo) {
                                    continue;
                                }
                                CLASS_INFO_LIST.add(classInfo);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.warn("", e);
                    }
                }
            }
        }
        return CLASS_INFO_LIST;
    }
}
