package org.github.project.generator.sdk;

import org.github.project.generator.AbstractGenerate;
import org.github.project.generator.Generator;
import org.github.project.generator.jdbc.ClassInfo;
import org.github.project.generator.utils.FilePathUtils;
import org.github.project.generator.utils.FreeMarkerTemplateUtils;
import org.github.project.generator.utils.PathConstant;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成 sdk
 * 提供给其他客户端的sdk
 */
@Slf4j
public class SdkProjectGenerateUtil extends AbstractGenerate implements Generator {

    @Override
    public Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos) throws Exception {
        filePath = filePath+"/"+projectName+"/"+projectName+"-sdk";
        //创建 项目 路径
        File projectFile = new File(filePath);
        //然后新建文件夹
        projectFile.mkdirs();
        //生成 pom.xml
        generatePom(filePath,projectName);
        log.info("    1, ----生成 pom.xml 文件成功");
        for(ClassInfo info: infos) {
            //生成sdk
            generateSdk(filePath, projectName);
            log.info("    2, ----生成 {}Client 成功", info.getClassName());
        }
        return getContext();
    }

    /**
     * 生成pom
     */
    public void generatePom(String filePath,String projectName) throws Exception {
        File mapperFile = new File(filePath+"/pom.xml");
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        putContext("projectName", projectName);
        process("/sdk/pom.ftl", out);
    }

    /**
     * 生成 Sdk
     */
    public void generateSdk(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"sdk";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        String className = FilePathUtils.getClassName(projectName);
        File projectFileErrorCode = new File(filePath+"/"+className+"Client.java");
        String packageName = FilePathUtils.getPackageName(projectName);
        String serverId = FilePathUtils.getServerId(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        putContext("packageName", packageName);
        putContext("serverId", serverId);
        putContext("className", className);
        process("/sdk/client.ftl", out);
    }

}
