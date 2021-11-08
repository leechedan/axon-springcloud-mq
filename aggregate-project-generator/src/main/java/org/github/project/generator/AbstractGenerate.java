package org.github.project.generator;

import cn.hutool.core.date.DateUtil;
import org.github.project.generator.jdbc.GlobleConfig;
import org.github.project.generator.utils.FreeMarkerTemplateUtils;
import org.github.project.generator.utils.StringUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractGenerate implements Generator {

    String projectPkgName;

    String filePath = GlobleConfig.getGlobleConfig().getRootPath();

    String pkgPath;
    /**
     * domain service mapper controller
     */
    String cate;

    /**
     * 分包名
     */
    String moduleName;

    private Map<String, Object> context = new HashMap<>();

    public AbstractGenerate() {

    }

    public AbstractGenerate(String cate, String moduleName, String projectName) {
        this.projectPkgName = StringUtil.addCharEnd(GlobleConfig.getGlobleConfig().getPackageName(), ".") + projectName.substring(projectName.lastIndexOf("-") + 1);
        if (StringUtils.isBlank(projectName)) {
            projectName = projectPkgName.replace('.', '-') + "-" + cate;
        }
        this.cate = cate;
        this.moduleName = moduleName;
        context.remove("moduleName");
        if (moduleName != null) {
            context.put("moduleName", moduleName);
        }
        if (StringUtils.isNoneBlank(cate)) {
            context.put("cate", cate);
        }
        this.pkgPath = String.format("%s/%s/src/main/java/%s/",
                filePath, projectName,
                projectPkgName.replace('.', '/'));
        if (!StringUtils.isBlank(cate)) {
            this.pkgPath = this.pkgPath + cate.replace('.', '/') + "/";
        }
        if (!StringUtils.isBlank(moduleName)) {
            this.pkgPath = this.pkgPath + moduleName + "/";
            context.put("packageName", String.format("%s.%s", projectPkgName, this.cate));
        } else {
            context.put("packageName", projectPkgName);
        }

    }

    protected Map<String, Object> putContext(String key, Object obj) {
        context.put(key, obj);
        return context;
    }

    protected void process(String templateResourcePath, Writer out) throws Exception {

        context.put("author", GlobleConfig.getGlobleConfig().getAuthorName());
        context.put("date", DateUtil.format(new Date(), "yyyy-MM-dd"));
        Template template = FreeMarkerTemplateUtils.getTemplate(templateResourcePath);
        template.process(context, out);
    }

    /***
     * @return 返回包名
     * @param templateResourcePath
     * @param fullFileDir  /分隔的子路径
     * @param fileName 文件名 带后缀
     * @throws Exception
     */
    protected String process(String templateResourcePath, String fullFileDir, String fileName) throws Exception {

        context.remove("subPkgName");
        String exactPkgName = (String) context.get("packageName");
        if (!StringUtils.isEmpty(this.moduleName)) {
            exactPkgName = exactPkgName + "." + this.moduleName;
        }
        if (!StringUtils.isEmpty(fullFileDir)) {
            exactPkgName = exactPkgName + "." + fullFileDir;
        }
        if (StringUtils.isNoneBlank(fullFileDir)) {
            putContext("subPkgName", "." + fullFileDir);
        }
        new File(pkgPath + fullFileDir.replace('.', '/')).mkdirs();
        String fullPath = pkgPath + fullFileDir.replace('.', '/') + "/" + fileName;
        log.debug("生成文件{}", fullPath);
        File mapperFile = new File(fullPath);
        FileOutputStream fos = new FileOutputStream(mapperFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);

        context.put("author", GlobleConfig.getGlobleConfig().getAuthorName());
        context.put("date", DateUtil.format(new Date(), "yyyy-MM-dd"));
        Template template = FreeMarkerTemplateUtils.getTemplate(templateResourcePath);
        template.process(context, out);
        return exactPkgName;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    @Override
    public void mergeContext(Map<String, Object> merge) {

        if (merge != null)
            merge.remove("moduleName");
        merge.entrySet().stream().filter(i -> !context.containsKey(i.getKey())).forEach(item -> context.put(item.getKey(), item.getValue()));
    }
}
