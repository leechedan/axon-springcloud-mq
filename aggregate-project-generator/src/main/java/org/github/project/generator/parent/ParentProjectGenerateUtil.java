package org.github.project.generator.parent;

import org.github.project.generator.AbstractGenerate;
import org.github.project.generator.Generator;
import org.github.project.generator.jdbc.ClassInfo;
import org.github.project.generator.utils.FreeMarkerTemplateUtils;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成 主项目，parent 项目
 */
@Slf4j
public class ParentProjectGenerateUtil extends AbstractGenerate
        implements Generator
{

    @Override
    public Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos) throws Exception {

        //生成 pom.xml
        generatePom(filePath,projectName,"");
        log.info("    1, ----生成 pom.xml 文件成功");
        //生成README.md
        generateReadme(filePath,projectName, "");
        log.info("    2, ----生成 README.md 文件成功");
        return null;
    }

    /**
     * 生成pom
     */
    public void generatePom(String filePath,String projectName,String projectDesc) throws Exception {
        File prjectFilePom = new File(filePath+"/"+projectName+"/pom.xml");
        Template template = FreeMarkerTemplateUtils.getTemplate("/parent/pom.ftl");
        FileOutputStream fos = new FileOutputStream(prjectFilePom);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        template.process(dataMap, out);
    }

    /**
     * 生成 README.md
     */
    public void generateReadme(String filePath,String projectName,String projectDesc) throws Exception {
        File prjectFilereadMe = new File(filePath+"/"+projectName+"/README.md");
        Template template = FreeMarkerTemplateUtils.getTemplate("/parent/readme.ftl");
        FileOutputStream fos = new FileOutputStream(prjectFilereadMe);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectDesc", projectDesc);
        template.process(dataMap, out);
    }

}
