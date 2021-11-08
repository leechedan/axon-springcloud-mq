package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${entityPkgName}.${classInfo.className};
import ${dtoPkgName}.${classInfo.className}DTO;
import ${cuRequestPkgName}.${classInfo.className}CreateRequest;
import ${cuRequestPkgName}.${classInfo.className}UpdateRequest;
import ${searchRequestPkgName}.${className}SearchRequest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @interface: ${classInfo.classComment}服务接口类
 * @date ${.now?string('yyyy-MM-dd')}
 * @author: ${author}
 */
public interface ${classInfo.className}Service extends IService<${classInfo.className}> {

    /**
     * @explain: 添加${classInfo.className}CreateRequest对象
     * @param:   ${classInfo.modelName}CreateRequest 对象参数
     * @return:  主键ID
     */
    Long insert(${classInfo.className}CreateRequest request);

    /**
     * @explain: 删除${classInfo.className}DTO对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @explain: 修改${classInfo.className}UpdateRequest对象
     * @param:   ${classInfo.modelName}UpdateRequest 对象参数
     * @return:  int
     */
    int updateByPrimaryKey(${classInfo.className}UpdateRequest request);

    /**
     * @explain: 查询${classInfo.className}DTO对象
     * @param:   id  对象参数
     * @return:  ${classInfo.className}DTO
     */
    ${classInfo.className}DTO selectByPrimaryKey(Long id);

    /**
     * @explain: 根据条件查询
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     */
    List<${classInfo.className}DTO> selectByRequest(${classInfo.className}SearchRequest request);

    /**
     * @explain: 查询 分页 ${classInfo.modelName}
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     * @param:   pageIndex  第几页
     * @param:   pageSize  每页显示多少条
     */
     IPage<${classInfo.className}DTO> selectByRequestPage(${classInfo.className}SearchRequest request);
}