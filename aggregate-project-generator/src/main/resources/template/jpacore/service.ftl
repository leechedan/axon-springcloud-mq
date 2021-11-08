package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${dtoPkgName}.${classInfo.className}DTO;
import ${entityPkgName}.${aggregate?cap_first}Entry;
import ${searchRequestPkgName}.${className}SearchRequest;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @interface: ${classInfo.classComment}服务接口类
 * @date ${.now?string('yyyy-MM-dd')}
 * @author: ${author}
 */
public interface ${classInfo.className}Service {

    /**
     * 添加${classInfo.className}Entry对象
     * @param:   ${classInfo.modelName}Entry 对象参数
     * @return:  主键ID
     */
    ${className}Entry save(${classInfo.className}Entry request);

    /**
     * @explain: 删除${classInfo.className}DTO对象
     * @param:   id  对象参数
     * @return:  int
     */
    void deleteByById(Long id);


    /**
     * 查询${classInfo.className}Entry对象
     * @param:   id  对象参数
     * @return:  ${classInfo.className}Entry
     */
    ${classInfo.className}Entry findById(Long id);

    List<${classInfo.className}Entry> findAll(${classInfo.className}SearchRequest request);

    /**
     * @explain: 查询 分页 ${classInfo.modelName}
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     * @param:   pageIndex  第几页
     * @param:   pageSize  每页显示多少条
     */
    Page<${classInfo.className}Entry> findByPageRequest(${classInfo.className}SearchRequest request);


    void deleteById(Long id);

    void replay();
}