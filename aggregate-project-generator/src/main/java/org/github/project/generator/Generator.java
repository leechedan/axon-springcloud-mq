package org.github.project.generator;

import org.github.project.generator.jdbc.ClassInfo;

import java.util.List;
import java.util.Map;

public interface Generator {

    Map<String, Object> generateProject(String filePath, String projectName, List<ClassInfo> infos) throws Exception ;

    void mergeContext(Map<String, Object> merge);
}
