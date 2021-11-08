package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${createCommandPkgName}.${aggregate?cap_first}CreateCommand;
import ${removeCommandPkgName}.${aggregate?cap_first}RemoveCommand;
import ${updateCommandPkgName}.${aggregate?cap_first}UpdateCommand;
import ${entityPkgName}.${aggregate?cap_first}Entry;
import ${entryRepositoryPkgName}.${aggregate?cap_first}EntryRepository;
import ${searchRequestPkgName}.${classInfo.className}SearchRequest;
import ${servicePkgName}.${classInfo.className}Service;
import ${dtoPkgName}.${classInfo.className}DTO;
import ${(commonPkgName)!}.web.BaseAdminController;
import ${(commonPkgName)!}.exception.BusinessError;
import ${(commonPkgName)!}.exception.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.axonframework.messaging.MetaData;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

/**
 * @author ${author}
 * @date ${date}
 */
@Api(value = "${aggregate?cap_first}用户侧api控制器", tags = "${classInfo.classComment}-open-api")
@Validated
@RequestMapping(value = "/user/${classInfo.pathName}")
@RestController
public class ${aggregate?cap_first}UserController extends BaseAdminController {

    @Autowired
    public ${classInfo.className}Service ${classInfo.modelName}Service;

    @Autowired
    private ${aggregate?cap_first}EntryRepository ${aggregate}EntryRepository;

    @ApiOperation(value="通过id查询详情信息", notes="通过id查询详情信息 api")
    @ApiImplicitParam(name = "id",  value = "查询实体的id", required = true, dataType = "Long", paramType="path")
    @GetMapping("/{id}")
    public ${classInfo.className}Entry get(@PathVariable("id") Long id){
        return ${classInfo.modelName}Service.findById(id);
    }

    @ApiOperation(value = "通过条件查询列表", notes = "通过条件查询列表 api")
    @GetMapping(value = "/list")
    public List<${classInfo.className}Entry> list(${classInfo.className}SearchRequest request){
        return ${classInfo.modelName}Service.findAll(request);
    }

    /*@ApiOperation(value = "通过条件查询分页数据", notes = "通过条件查询分页数据 api")
    @GetMapping(value = "/page")
    public Page<${classInfo.className}Entry> listByPage(${classInfo.className}SearchRequest request){
        return ${classInfo.modelName}Service.findByPageRequest(request);
    }*/

    @ApiOperation(value = "${aggregate}-create")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid ${aggregate?cap_first}CreateCommand command) {
        send(command);
    }

    @ApiOperation(value = "${aggregate}-update")
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid ${aggregate?cap_first}UpdateCommand command) {
        send(command);
    }

    @ApiOperation(value = "${aggregate}-delete")
    @DeleteMapping(value = "/remove")
    public void delete(@RequestBody @Valid ${aggregate?cap_first}RemoveCommand command) {
        send(command);
    }
}
