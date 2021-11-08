package org.github.project.generator.rest;

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
 * 生成 rest 工程，
 */
@Slf4j
public class RestProjectGenerateUtil extends AbstractGenerate implements Generator {


    public RestProjectGenerateUtil(String cate, String moduleName, String projectName) {
        super(cate, moduleName, projectName);
    }


    @Override
    public Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos
    ) throws Exception {


        /*if(isDockerProject){
            //生成 docker 项目 pom.xml
            generateDockerPom(filePath,projectName);
            log.info("    1, ----生成 pom.xml 文件成功");
        }else{*/
            //生成 普通项目 pom.xml
//            generateBasePom(filePath,projectName);
//            log.info("    1, ----生成 pom.xml 文件成功");
//        }
        //生成mybatis-config.xml
//        generateMybatisConfigXml(filePath,projectName);
//        log.info("    2, ----生成 mybatis-config.xml 文件成功");

        //生成logback-spring.xml
//        generateLogbackSpringXml(filePath,projectName);
//        log.info("    3, ----生成 logback-spring.xml 文件成功");

        //生成bootstrap.yaml
//        generateBootstrapYaml(filePath,projectName);
//        log.info("    4, ----生成 bootstrap.yaml 文件成功");

        //生成application.yaml
//        generateApplicationYaml(filePath,projectName);
//        log.info("    5, ----生成 application.yaml 文件成功");

//        ConfigGenerateUtil.generateConfig(filePath,projectName);
//        log.info("    6, ----生成 config配置 文件成功");

        //生成message
//        MessageGenerateUtil.generateMessages(filePath,projectName);
//        log.info("    7, ----生成 message 文件成功");

       /* if(isDockerProject){
            //生成DockerFile
            generateDockerFile(filePath,projectName);
            log.info("    8, ----生成 DockerFile 文件成功");
        }*/

        //生成main启动类
//        generateStartMain(filePath, projectName, "projectDesc");
//        log.info("    9, ----生成 启动类 文件成功");

        for (ClassInfo info:infos) {
            putContext("classInfo", info);
            putContext("className", info.getClassName());
            putContext("classDesc", info.getClassComment());
            //生成controller类
            generateController(filePath, projectName, info);
            log.info("    10, ----生成 {}Controller 文件成功", info.getClassName());
        }

        //生成swagger类
//        generateSwagger(filePath, projectName, "projectDesc");
//        log.info("    11, ----生成 swagger 文件成功");

        //生成ThreadPoolTaskConfig类
//        generateThreadPoolTaskConfig(filePath, projectName);
//        log.info("    12, ----生成 ThreadPoolTaskConfig 文件成功");

        return getContext();
    }

    /**
     * 生成普通pom.xml
     * filePath = .......-rest
     */
    public void generateBasePom(String filePath,String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        process("/rest/pom-base.ftl", out);
    }

    /**
     * 生成docker项目pom.xml
     * filePath = .......-rest
     */
    public void generateDockerPom(String filePath,String projectName) throws Exception {
        File projectFilePom = new File(filePath+"/pom.xml");
        FileOutputStream fos = new FileOutputStream(projectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        putContext("projectName", projectName);
        putContext("className", className);
        putContext("packageName", packageName);
        process("/rest/pom-docker.ftl", out);
    }

    /**
     * 生成 mybatis-config.xml
     * filePath = .......-rest
     */
    public void generateMybatisConfigXml(String filePath,String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/mybatis";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/mybatis-config.xml");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        process("/rest/mybatis/mybatisconfig.ftl", out);
    }

    /**
     * 生成 logback-spring.xml
     * filePath = .......-rest
     */
    public void generateLogbackSpringXml(String filePath, String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH+"/logs";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/logback-spring.xml");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        process("/rest/logs/logbackspring.ftl", out);
    }

    /**
     * 生成 bootstrap.yaml
     * filePath = .......-rest
     */
    public void generateBootstrapYaml(String filePath, String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/bootstrap.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/bootstrap.ftl");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        template.process(dataMap, out);
    }

    /**
     * 生成 application.yaml
     * filePath = .......-rest
     */
    public void generateApplicationYaml(String filePath, String projectName) throws Exception {
        filePath = filePath+PathConstant.RESOURCEPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/application.yaml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/rest/application.ftl");
        String packageName = FilePathUtils.getPackageName(projectName);
        String mainName = FilePathUtils.getMainName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        putContext("packageName", packageName);
        putContext("mainName", mainName);
        process("/rest/application.ftl", out);
    }

    /**
     * 生成 DockerFile
     * filePath = .......-rest
     */
    public void generateDockerFile(String filePath, String projectName) throws Exception {
        filePath = filePath+PathConstant.DOCKERPATH;
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"/Dockerfile");
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        putContext("projectName", projectName);
        process("/rest/docker/dockerfile.ftl", out);
    }

    /**
     * 生成 StartMain
     * filePath = .......-rest
     */
    public void generateStartMain(String filePath,String projectName,String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName);
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+FilePathUtils.getClassName(projectName)+"Application.java");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        putContext("projectName", projectName);
        putContext("projectDesc", projectDesc);
        putContext("className", className);
        putContext("packageName", packageName);
        process("/rest/java/main.ftl", out);
    }

    /**
     * 生成 swagger
     * filePath = .......-rest
     */
    public void generateSwagger(String filePath, String projectName, String projectDesc) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"config/";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"SwaggerConfig.java");
        String className = FilePathUtils.getClassName(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        process("/rest/java/swagger.ftl", out);
    }

    /**
     * 生成 线程池配置
     * filePath = .......-rest
     */
    public void generateThreadPoolTaskConfig(String filePath, String projectName) throws Exception {
        filePath = filePath+PathConstant.JAVAPATH+FilePathUtils.getPackPath(projectName)+"config/";
        File srcPath = new File(filePath);
        srcPath.mkdirs();
        File projectFileErrorCode = new File(filePath+"ThreadPoolTaskConfig.java");
        String className = FilePathUtils.getClassNameFirst(projectName);
        String packageName = FilePathUtils.getPackageName(projectName);
        FileOutputStream fos = new FileOutputStream(projectFileErrorCode);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        process("/rest/java/threadpooltaskconfig.ftl", out);
    }

    /**
     * 生成 Controller
     * filePath = .......-rest
     */
    public void generateController(String filePath,String projectName, ClassInfo info) throws Exception {
        process("/rest/java/controller.ftl", "controller", info.getClassName() + "Controller.java");
    }
}
