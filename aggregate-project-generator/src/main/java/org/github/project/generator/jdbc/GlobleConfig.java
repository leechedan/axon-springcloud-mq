package org.github.project.generator.jdbc;


import org.github.project.generator.factory.PropertiesFactory;

import java.io.IOException;

/**
 * ******************************
 * author：
 * createTime:   2020/1/9 17:11
 * description:  单例模式 -> 全局配置信息
 * version:      V1.0
 * ******************************
 */
public final class GlobleConfig {

    // 配置信息
    private volatile static ConfigurationInfo CONFIGURATIONInfo = null;

    /***
     * 获取全局配置
     * 单例模式 双重锁校验
     */
    public static ConfigurationInfo getGlobleConfig() {
        if (null == CONFIGURATIONInfo) {
            synchronized (GlobleConfig.class) {
                if (null == CONFIGURATIONInfo) {
                    try {
                        PropertiesFactory.loadProperties();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONFIGURATIONInfo;
    }

    public static void setGlobleConfig(ConfigurationInfo CONFIGURATIONInfo) {
        GlobleConfig.CONFIGURATIONInfo = CONFIGURATIONInfo;
    }

    private GlobleConfig() {}
}
