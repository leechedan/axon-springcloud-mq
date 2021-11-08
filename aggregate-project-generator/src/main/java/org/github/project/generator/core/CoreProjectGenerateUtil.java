package org.github.project.generator.core;

import org.github.project.generator.AbstractGenerate;
import org.github.project.generator.Generator;
import org.github.project.generator.jdbc.ClassInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 生成 core 工程
 */
@Slf4j
public class CoreProjectGenerateUtil extends AbstractGenerate implements Generator {


    public CoreProjectGenerateUtil(String cate, String moduleName, String projectName) {
        super(cate, moduleName, projectName);
    }

    @Override
    public Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos) throws Exception {

        //生成 pom.xml
//        generatePom(filePath,projectName);
//        log.info("    1, ----生成 pom.xml 文件成功");
        //生成 README.md
//        generateReadMe(filePath,projectName);
//        log.info("    2, ----生成 README.md 文件成功");

        for (ClassInfo info:infos) {
            putContext("classInfo", info);
            putContext("aggregate", info.getClassName());
            putContext("className", info.getClassName());
            putContext("classDesc", info.getClassComment());
            //生成Mapper.java
//            generateMapper(info);
//            log.info("    2, ----生成 {}Mapper.java 成功", info.getClassName());
            //生成Mapper.xml
//            generateMapperXml(info);
//            log.info("    3, ----生成 {}Mapper.xml 成功", info.getClassName());
            //生成Service.java
            generateService(info);
            generateServiceImpl(info);
            generateUserController(info);
            generateAdminController(info);
            generateUpcaster(info);
        }
        return getContext();
    }

    public void generateUserController(ClassInfo info) throws Exception {
        String cuRequest = process("/aggre/UserController.java.ftl", "web.user", info.getClassName()+ "UserController.java");
        putContext("userControllerPkgName", cuRequest);
    }

    public void generateAdminController(ClassInfo info) throws Exception {
        String cuRequest = process("/aggre/AdminController.java.ftl", "web.admin", info.getClassName()+ "AdminController.java");
        putContext("adminControllerPkgName", cuRequest);
    }
    /**
     * 生成pom.xml
     * filePath = .......-core
     */
    public void generatePom(String filePath, String projectName) throws Exception {
        File mapperFile = new File(filePath+"/pom.xml");
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        putContext("projectName", projectName);
        process("/core/pom.ftl", out);
    }

    /**
     * 生成 README.md
     */
    public void generateReadMe(String filePath, String projectName) throws Exception {
        File readMeFile = new File(filePath+"/README.md");
        FileOutputStream fos = new FileOutputStream(readMeFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        process("/core/readme.ftl", out);
    }

    /**
     * 生成 Service
     * filePath = .......-core
     */
    public void generateService(ClassInfo info) throws Exception {
        String servicePkgName = process("/jpacore/service.ftl", "query.service", info.getClassName() + "Service.java");
        putContext("servicePkgName", servicePkgName);
    }

    /**
     * 生成 ServiceImpl
     * filePath = .......-core
     */
    public void generateServiceImpl(ClassInfo info) throws Exception {
        String servicePkgName = process("/jpacore/serviceImpl.ftl", "query.service.impl",
                info.getClassName() + "ServiceImpl.java");
        putContext("serviceImplPkgName", servicePkgName);
    }

    public void generateMain(ClassInfo info) throws Exception {
        String servicePkgName = process("/rest/java/main.ftl", "",
                info.getClassName() + "Application.java");
        putContext("mainPkgName", servicePkgName);
    }

    public void generateUpcaster(ClassInfo info) throws Exception {
        String servicePkgName = process("/aggre/Upcaster.java.ftl", "command.upcaster",
                                        info.getClassName() + "CreatedEventUpcaster.java");
        putContext("mainPkgName", servicePkgName);
    }
}
