package org.github.project.generator;

import org.github.project.generator.bean.ApiProjectGenerateUtil;
import org.github.project.generator.bean.BeanProjectGenerateUtil;
import org.github.project.generator.common.CommonProjectGenerateUtil;
import org.github.project.generator.core.CoreProjectGenerateUtil;
import org.github.project.generator.factory.ClassInfoFactory;
import org.github.project.generator.jdbc.ClassInfo;
import org.github.project.generator.jdbc.GlobleConfig;
import org.github.project.generator.parent.ParentProjectGenerateUtil;
import org.github.project.generator.rest.RestProjectGenerateUtil;
import org.github.project.generator.sdk.SdkProjectGenerateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动类生成
 * 会生成到
 */
@Slf4j
public class GenerateStartUp {

    //生成项目地址
    public final static String filePath = "./project";
    //项目名称
    public final static String projectName = "xin-order";
    //项目说明
    public final static String projectDesc = "订单服务";

    //是否是docker项目，true-->是，false-->否
    public final static Boolean isDockerProject = false;

    public static void main(String[] args) {
        String cate = "2";
        if (args != null && args.length == 1) {
            cate = args[0];
        }
        List<ClassInfo> infos = ClassInfoFactory.getClassInfoList();
        //1: parent 2:common 3:Bean 4:core 5:Rest  6:sdk
        Map<String, Object> merge = new HashMap<>();
        Generator generator = null;
        merge.put("basePkgName", "org.github.axon.tag.base.domain.common");
        merge.put("commonPkgName", "org.github.axon.tag.common");
        try {
            switch (cate) {
                case "1":
                    //生成 parent 项目
                    ParentProjectGenerateUtil utilProject = new ParentProjectGenerateUtil();
                    log.info("1, ----生成parent项目");
                    utilProject.generateProject(filePath, projectName, null);
//                    break;

                case "0":
                    //生成 common 项目
                    CommonProjectGenerateUtil utilCommon = new CommonProjectGenerateUtil();
                    log.info("2, ----生成common项目");
                    utilCommon.generateCommonProject(filePath, projectName, projectDesc);
//                    break;

                case "2":
                    //生成 common 项目
                    generator = new ApiProjectGenerateUtil("api",
                            GlobleConfig.getGlobleConfig().getModuleName(), "aggre-api");
                    log.info("2, ----生成common项目");
                    generator.mergeContext(merge);
                    merge = generator.generateProject(filePath, projectName, infos);
//                    break;

                case "3":
                    log.info("3, ----生成Bean项目.");
                    generator = new BeanProjectGenerateUtil(GlobleConfig.getGlobleConfig().getLinkDirName(),
                            GlobleConfig.getGlobleConfig().getModuleName(), GlobleConfig.getGlobleConfig().getProjectName());
                    generator.mergeContext(merge);
                    merge = generator.generateProject(filePath, projectName, infos);
//                    break;

                case "4":
                    log.info("4, ----生成Core项目.");
                    generator = new CoreProjectGenerateUtil(GlobleConfig.getGlobleConfig().getLinkDirName(),
                            GlobleConfig.getGlobleConfig().getModuleName(), GlobleConfig.getGlobleConfig().getProjectName());
                    generator.mergeContext(merge);
                    merge = generator.generateProject(filePath, projectName, infos);
                    break;

                case "5":
                    log.info("5, ----生成Rest项目.");
                    generator = new RestProjectGenerateUtil(GlobleConfig.getGlobleConfig().getLinkDirName(),
                            GlobleConfig.getGlobleConfig().getModuleName(), GlobleConfig.getGlobleConfig().getProjectName());
                    generator.mergeContext(merge);
                    merge = generator.generateProject(filePath, projectName, infos);
                    break;

                case "6":
                    generator = new SdkProjectGenerateUtil();
                    log.info("6, ----生成Sdk项目.");
                    generator.mergeContext(merge);
                    generator.generateProject(filePath, projectName, infos);
                    break;
            }
            log.info("项目已将生成，请到 ：请到 ：" + filePath + "路径下查看查看");
        } catch (Exception e) {
            log.info("", e);
        }

    }
}