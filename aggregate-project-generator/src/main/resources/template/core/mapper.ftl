package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${entityPkgName}.${className};
import ${dtoPkgName}.${className}DTO;
import ${cuRequestPkgName}.${classInfo.className}SearchRequest;
import java.util.List;
import org.apache.ibatis.annotations.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @desc ${classInfo.classComment}接口
 * @interface: ${className}Mapper
 * @date: ${.now?string('yyyy-MM-dd')}
 * @author: ${author}
 */
public interface ${className}Mapper extends BaseMapper<${className}> {


    /**
     * @explain: 查询${className}DTO对象
     * @param:   SearchRequest  对象参数
     * @return:  ${className}DTO
     */
    List<${className}DTO> selectByRequest(@Param("page")Page<${className}DTO> page,
          @Param("param")${className}SearchRequest ${classInfo.modelName}SearchRequest);

}
