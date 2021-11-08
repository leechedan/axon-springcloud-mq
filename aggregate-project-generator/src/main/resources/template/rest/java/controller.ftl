package ${packageName}.${(moduleName)!}${(subPkgName)!};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import ${servicePkgName}.${classInfo.className}Service;
import ${searchRequestPkgName}.${classInfo.className}SearchRequest;
import ${dtoPkgName}.${classInfo.className}DTO;

import java.util.List;

/**
 * @desc ${classInfo.classComment}控制器接口
 * @date: ${.now?string('yyyy-MM-dd')}
 * @author: ${author}
 */
@Slf4j
@RestController
@RequestMapping("/open-api/v1/${classInfo.pathName}")
@Api(value = "${classInfo.classComment} API",tags = {"${classInfo.classComment} api操作接口"}, protocols = "http",consumes = "",produces = "")
public class ${classInfo.className}Controller {

    @Autowired
    public ${classInfo.className}Service ${classInfo.modelName}Service;

    @ApiOperation(value = "新增数据", notes = "新增数据 api")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${classInfo.classComment}CreateRequest", value = "新增对象实体",  required = true,
            dataType = "${classInfo.className}CreateRequest", paramType = "body")
    })
    @PostMapping()
    public Payload<Long> add(@RequestBody ${classInfo.className}CreateRequest request){
        return new Payload<>(${classInfo.modelName}Service.insert(request));
    }

    @ApiOperation(value = "修改数据", notes = "修改数据 api")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${classInfo.classComment}DTO", value = "修改对象实体", required = true,
            dataType = "${classInfo.className}DTO", paramType = "body")
    })
    @PutMapping()
    public Payload<Integer> update(@RequestBody ${classInfo.className}UpdateRequest request){
        return new Payload<>(${classInfo.modelName}Service.updateByPrimaryKey(request));
    }

    @ApiOperation(value="通过id删除数据", notes="通过id删除数据 api")
    @ApiImplicitParam(name = "id",  value = "删除实体的id", required = true, dataType = "Long", paramType="path")
    @DeleteMapping("/{id}")
    public Payload<Integer> delete(@PathVariable("id") Long id){
        return new Payload<>(${classInfo.modelName}Service.deleteByPrimaryKey(id));
    }

    @ApiOperation(value="通过id查询详情信息", notes="通过id查询详情信息 api")
    @ApiImplicitParam(name = "id",  value = "查询实体的id", required = true, dataType = "Long", paramType="path")
    @GetMapping("/{id}")
    public Payload<${classInfo.className}DTO> get(@PathVariable("id") Long id){
        return new Payload<>(${classInfo.modelName}Service.selectByPrimaryKey(id));
    }

    @ApiOperation(value = "通过条件查询列表", notes = "通过条件查询列表 api")
    @GetMapping(value = "/list")
    public Payload<List<${classInfo.className}DTO>> list(${classInfo.className}SearchRequest request){
        return new Payload<>(${classInfo.modelName}Service.selectByRequest(request));
    }

    @ApiOperation(value = "通过条件查询分页数据", notes = "通过条件查询分页数据 api")
    @GetMapping(value = "/page")
    public Payload<PageBean<${classInfo.className}DTO>> listByPage(${classInfo.className}SearchRequest request){
        return new Payload<>(new PageBean<>(${classInfo.modelName}Service.selectByRequestPage(request)));
    }
}