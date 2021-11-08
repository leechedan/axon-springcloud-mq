package org.github.project.generator.bean;

import org.github.project.generator.AbstractGenerate;
import org.github.project.generator.Generator;
import org.github.project.generator.jdbc.ClassInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成 bean工程
 */
@Slf4j
public class BeanProjectGenerateUtil extends AbstractGenerate implements Generator {

    public BeanProjectGenerateUtil(String cate, String moduleName, String projectName) {
        super(cate, moduleName, projectName);
    }

    @Override
    public Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos) throws Exception {

        //生成 pom.xml
//        generatePom(filePath, projectName);
        log.info("    1, ----生成 pom.xml 文件成功");
        for(ClassInfo info : infos) {
            putContext("classInfo", info);
            putContext("aggregate", info.getClassName());
            putContext("className", info.getClassName());
            putContext("classDesc", info.getClassComment());
            generateSearchRequest(info);
            generateDto(info);
            generateCommandListener(info);
            generateEventListener(info);
            generateAggregate(info);
            generateAggConfig(info);
            generateEntry(info);
            generateEntryRepository(info);
            generateListener(info);
            generateHandler(info);
        }
        return getContext();

    }

    /**
     * 生成pom
     * filePath = .......-bean
     */
    public void generatePom(String filePath, String projectName) throws Exception {
        File mapperFile = new File(filePath + "/pom.xml");
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        putContext("projectName", projectName);
        process("/aggre/pom.ftl", out);
    }

    public void generateSearchRequest(ClassInfo info) throws Exception {
        String requestPkgName = process("/bean/search.ftl",  "query.request", info.getClassName() + "SearchRequest.java");
        putContext("searchRequestPkgName", requestPkgName);
    }

    public void generateDto(ClassInfo info) throws Exception {
        String dto = process("/bean/dto.ftl", "query.dto", info.getClassName() + "DTO.java");
        putContext("dtoPkgName", dto);
    }

    public void generateAggConfig(ClassInfo info) throws Exception {
        String requestPkgName = process("/aggre/Config.java.ftl",  "command.config", info.getClassName() + "Config.java");
        putContext("aggConfigPkgName", requestPkgName);
    }

    public void generateEntry(ClassInfo info) throws Exception {
        String entityPkgName = process("/aggre/Entry.java.ftl", "query.entry", info.getClassName() + "Entry.java");
        putContext("entityPkgName", entityPkgName);
    }
    public void generateCommandListener(ClassInfo info) throws Exception {
        String entityPkgName = process("/aggre/CommandListener.java.ftl", "command.listener", "I"+info.getClassName() + "Command.java");
        putContext("iCommandPkgName", entityPkgName);
    }

    public void generateEntryRepository(ClassInfo info) throws Exception {
        String requestPkgName = process("/aggre/EntryRepository.java.ftl",  "query.repository", info.getClassName() + "EntryRepository.java");
        putContext("entryRepositoryPkgName", requestPkgName);
    }

    public void generateEventListener(ClassInfo info) throws Exception {
        String voPkgName = process("/aggre/EventListener.java.ftl", "api.event", "I"+info.getClassName()+"Event.java");
        putContext("iEventPkgName", voPkgName);
    }

    public void generateListener(ClassInfo info) throws Exception {
        String voPkgName = process("/aggre/Listener.java.ftl", "query.listener", info.getClassName()+"Listener.java");
        putContext("listenerPkgName", voPkgName);
    }


    public void generateHandler(ClassInfo info) throws Exception {
        String voPkgName = process("/aggre/Handle.java.ftl", "command.handle", info.getClassName()+"QueryHandler.java");
        putContext("handlePkgName", voPkgName);
    }

    public void generateAggregate(ClassInfo info) throws Exception {
        String aggregatePkgName = process("/aggre/Aggregate.java.ftl", "command.aggregate", info.getClassName()+ "Aggregate.java");
        putContext("aggregatePkgName", aggregatePkgName);
    }

}
