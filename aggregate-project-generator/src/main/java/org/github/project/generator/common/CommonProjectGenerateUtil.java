package org.github.project.generator.common;

import org.github.project.generator.utils.FilePathUtils;
import org.github.project.generator.utils.FreeMarkerTemplateUtils;
import org.github.project.generator.utils.PathConstant;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成 common 工程
 */
@Slf4j
public class CommonProjectGenerateUtil {

    public void generateCommonProject(String filePath,String projectName,String projectDesc) throws Exception {

        //生成 pom.xml
        generatePom(filePath,projectName);
        log.info("    1, ----生成 pom.xml 文件成功");
        //生成ErrorCode.java
        generateErrorCode(filePath,projectName,projectDesc);
        log.info("    2, ----生成 ErrorCodeEnum 成功");
        //生成generateException.java
        generateException(filePath,projectName,projectDesc);
        log.info("    3, ----生成 Exception 成功");
        //生成Constant.java
        generateConstant(filePath,projectName,projectDesc);
        log.info("    4, ----生成 Constant 成功");
        //生成Enums.java
        generateEnums(filePath,projectName,projectDesc);
        log.info("    5, ----生成 Enums 成功");
        //生成Utils.java
        generateUtils(filePath,projectName,projectDesc);
        log.info("    6, ----生成 Utils 成功");

    }

    /**
     * 生成pom.xml
     * filePath = .......-common
     */
    public void generatePom(String filePath, String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/common/pom.ftl");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        template.process(dataMap, out);
    }

    /**
     * 生成 ErrorCodeEnum
     * filePath = .......-common
     */
    public void generateErrorCode(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"common/errorcode";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/"+FilePathUtils.getClassName(projectName)+"ErrorCode.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/common/errorcode.ftl");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Exception
     * filePath = .......-common
     */
    public void generateException(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"common/exception";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/"+FilePathUtils.getClassName(projectName)+"Exception.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/common/exception.ftl");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        dataMap.put("className", className);
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Constant
     * filePath = .......-common
     */
    public void generateConstant(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"common/constant";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/BusinessConstant.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/common/constant.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Constant
     * filePath = .......-common
     */
    public void generateEnums(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"common/enums";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/OrderStatusEnum.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/common/enums.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

    /**
     * 生成 Utils
     * filePath = .......-common
     */
    public void generateUtils(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath + PathConstant.JAVAPATH + FilePathUtils.getPackPath(projectName) + "common/utils";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/StringUtil.java");
        Template template = FreeMarkerTemplateUtils.getTemplate("/common/utils.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageName);
        template.process(dataMap, out);
    }

}
