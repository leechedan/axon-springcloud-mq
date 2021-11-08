package org.github.project.generator.bean;

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
public class ApiProjectGenerateUtil extends AbstractGenerate implements Generator {


    public ApiProjectGenerateUtil(String cate, String moduleName, String projectName) {
        super(cate, moduleName, projectName);
    }

    @Override
    public Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos) throws Exception {


        for (ClassInfo info:infos) {

            putContext("classInfo", info);
            putContext("aggregate", info.getClassName());
            putContext("className", info.getClassName());
            putContext("classDesc", info.getClassComment());
            //生成Api
            generateCreateCommand(info);
            generateCreatedEvent(info);
            generateUpdateCommand(info);
            generateUpdatedEvent(info);
            generateRemoveCommand(info);
            generateRemovedEvent(info);
            generateQueryCommand(info);
        }
        return getContext();
    }

    /**
     * 生成 Client
     * filePath = .......-core
     */
    public void generateClient(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/client.ftl", "", info.getClassName()+ "MiddleApi.java");
        putContext("clientPkgName", clientPkgName);
    }


    public void generateCreatedEvent(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/CreatedEvent.java.ftl", "event", info.getClassName()+ "CreatedEvent.java");
        putContext("createdEventPkgName", clientPkgName);
    }


    public void generateCreateCommand(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/CreateCommand.java.ftl", "command", info.getClassName()+ "CreateCommand.java");
        putContext("createCommandPkgName", clientPkgName);
    }

    public void generateUpdatedEvent(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/UpdatedEvent.java.ftl", "event", info.getClassName()+ "UpdatedEvent.java");
        putContext("updatedEventPkgName", clientPkgName);
    }


    public void generateUpdateCommand(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/UpdateCommand.java.ftl", "command", info.getClassName()+ "UpdateCommand.java");
        putContext("updateCommandPkgName", clientPkgName);
    }

    public void generateRemovedEvent(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/RemovedEvent.java.ftl", "event", info.getClassName()+ "RemovedEvent.java");
        putContext("removedEventPkgName", clientPkgName);
    }

    public void generateRemoveCommand(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/RemoveCommand.java.ftl", "command", info.getClassName()+ "RemoveCommand.java");
        putContext("removeCommandPkgName", clientPkgName);
    }

    public void generateQueryCommand(ClassInfo info) throws Exception {
        String clientPkgName = process("/aggre/QueryCommand.java.ftl", "command", info.getClassName()+ "QueryCommand.java");
        putContext("queryCommandPkgName", clientPkgName);
    }

}
