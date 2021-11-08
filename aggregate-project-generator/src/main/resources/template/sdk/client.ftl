package ${clientPackageName}.client;

import ${cuRequestPkgName}.${classInfo.className}CreateRequest;
import ${cuRequestPkgName}.${classInfo.className}UpdateRequest;
import ${searchRequestPkgName}.${classInfo.className}SearchRequest;
import ${dtoPkgName}.${classInfo.className}DTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * @desc: ${classInfo.classComment}
 * @author: ${author}
 * @date: ${date}
 */
@FeignClient(value = "${classInfo.pathName}-api",path = "/v1/${classInfo.pathName}")
public interface ${className}Client {

    @ApiOperation(value = "新增数据", notes = "新增数据 api")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${classInfo.modelName}CreateRequest", value = "新增对象实体",  required = true,
                dataType = "${classInfo.className}CreateRequest", paramType = "body")
    })
    @PostMapping()
    PayLoad add(@RequestBody ${classInfo.className}CreateRequest ${classInfo.modelName}CreateRequest);

    @ApiOperation(value = "修改数据", notes = "修改数据 api")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${classInfo.modelName}UpdateRequest", value = "修改对象实体", required = true,
                dataType = "${classInfo.className}UpdateRequest", paramType = "body")
    })
    @PutMapping()
    PayLoad<Integer> update(@RequestBody ${classInfo.className}UpdateRequest ${classInfo.modelName}UpdateRequest);

    @ApiOperation(value="通过id删除数据", notes="通过id删除数据 api")
    @ApiImplicitParam(name = "id",  value = "删除实体的id", required = true, dataType = "Long", paramType="path")
    @DeleteMapping("/{id}")
    PayLoad<Integer> delete(@PathVariable("id") Long id);

    @ApiOperation(value="通过id查询详情信息", notes="通过id查询详情信息 api")
    @ApiImplicitParam(name = "id",  value = "查询实体的id", required = true, dataType = "Long", paramType="path")
    @GetMapping("/{id}")
    PayLoad<${classInfo.className}Dto> get(@PathVariable("id") Long id);

    @ApiOperation(value = "通过条件查询列表", notes = "通过条件查询列表 api")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${classInfo.modelName}Dto", value = "修改对象实体", required = true, dataType = "DemoBo", paramType = "body")
    })
    @GetMapping(value = "/list")
    PayLoad<List<${classInfo.className}Dto>> list(@RequestBody ${classInfo.className}SearchRequest ${classInfo.modelName}SearchRequest);

    @ApiOperation(value = "通过条件查询分页数据", notes = "通过条件查询分页数据 api")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${classInfo.modelName}SearchRequest", value = "查询条件实体", required = true,
                dataType = "${classInfo.className}SearchRequest", paramType = "body"),
        @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping(value = "/list-by-page")
    PayLoad<IPage<${classInfo.className}Dto>> listByPage(@RequestBody ${classInfo.className}SearchRequest ${classInfo.modelName}SearchRequest,
                                                @RequestParam("pageIndex") Integer pageIndex,
                                                @RequestParam("pageSize") Integer pageSize);

}
