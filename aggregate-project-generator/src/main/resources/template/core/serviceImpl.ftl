package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${mapperPkgName}.${className}Mapper;
import ${servicePkgName}.${className}Service;
import ${dtoPkgName}.${classInfo.className}DTO;
import ${entityPkgName}.${classInfo.className};

import ${cuRequestPkgName}.${classInfo.className}CreateRequest;
import ${cuRequestPkgName}.${classInfo.className}UpdateRequest;
import ${searchRequestPkgName}.${className}SearchRequest;
import ${basePkgName}.util.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @class: ${classInfo.classComment}服务实现类
 * @date ${.now?string('yyyy-MM-dd')}
 * @author: ${author}
 */
@Slf4j
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {

    @Autowired
    private ${className}Mapper ${classInfo.modelName}Mapper;

    /**
     * @explain: 添加${className}DTO对象
     * @param:   ${classInfo.modelName}DTO 对象参数
     * @return:  int
     */
    @Override
    public Long insert(${className}CreateRequest request) {
        ${className} ${classInfo.modelName} = ModelMapper.map(request, ${className}.class);
        ${classInfo.modelName}Mapper.insert(${classInfo.modelName});
        return ${classInfo.modelName}.getId();
    }

    /**
     * @explain: 删除${className}DTO对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return ${classInfo.modelName}Mapper.deleteById(id);
    }

    /**
     * @explain: 修改${className}UpdateRequest 对象
     * @param:   request  对象参数
     * @return:  int
     */
    @Override
    public int updateByPrimaryKey(${className}UpdateRequest request) {
        ${className} ${classInfo.modelName} = ModelMapper.map(request, ${className}.class);
        ${classInfo.modelName}.set${classInfo.key.methodField}(request.get${classInfo.key.methodField}());
        return ${classInfo.modelName}Mapper.updateById(${classInfo.modelName});
    }

    /**
     * @explain: 查询${className}DTO对象
     * @param:   id  对象参数
     * @return:  ${className}DTO
     */
    @Override
    public ${className}DTO selectByPrimaryKey(Long id) {
        return ModelMapper.map(${classInfo.modelName}Mapper.selectById(id),${className}DTO.class);
    }

    /**
     * @explain: 根据条件查询
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     */
    @Override
    public List<${className}DTO> selectByRequest(${className}SearchRequest request){
        //具体的逻辑结合需求去写
        List<${className}DTO> list = ${classInfo.modelName}Mapper.selectByRequest(null, request);

        return ModelMapper.mapToList(list, ${className}DTO.class);
    }

    /**
     * @explain: 查询 分页 ${classInfo.modelName}
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     * @param:   pageIndex  第几页
     * @param:   pageSize  每页显示多少条
     */
    @Override
    public IPage<${className}DTO> selectByRequestPage(${className}SearchRequest request) {

        Page page = request.page();
        List list = ${classInfo.modelName}Mapper.selectByRequest(request.page(), request);
        IPage<${className}DTO> ${classInfo.modelName}DtoPage = ObjectCloneUtils.convertPage(page, ${className}DTO.class);
        page.setRecords(list);
        return page;
    }
}
