package ${packageName}.${(moduleName)!}${(subPkgName)!};

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ${entityPkgName}.${aggregate?cap_first}Entry;
import ${entryRepositoryPkgName}.${aggregate?cap_first}EntryRepository;
import ${servicePkgName}.${className}Service;
import ${dtoPkgName}.${classInfo.className}DTO;
import ${searchRequestPkgName}.${className}SearchRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;

import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;

/**
 * @class: ${classInfo.classComment}服务实现类
 * @date ${.now?string('yyyy-MM-dd')}
 * @author: ${author}
 */
@Slf4j
@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${aggregate?cap_first}EntryRepository ${aggregate?uncap_first}EntryRepository;

    @Autowired
    private EventProcessingConfiguration epc;

    /**
     * @explain: 添加${className}DTO对象
     * @param:   ${classInfo.modelName}DTO 对象参数
     * @return:  int
     */
    @Override
    public ${className}Entry save(${className}Entry entry) {
        return ${aggregate?uncap_first}EntryRepository.save(entry);
    }

    /**
     * @explain: 删除${className}DTO对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public void deleteByById(Long id) {
        ${aggregate?uncap_first}EntryRepository.deleteById(id);
    }

    /**
     * 查询${aggregate?uncap_first}Entry对象
     * @param:   id  对象参数
     * @return:  ${aggregate?uncap_first}Entry
     */
    @Override
    public ${aggregate?cap_first}Entry findById(Long id) {
        return  ${aggregate?uncap_first}EntryRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("cannot find entity by [%s]", id)));
    }

    /**
     * @explain: 根据条件查询
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     */
    @Override
    public List<${className}Entry> findAll(${className}SearchRequest request){
        //具体的逻辑结合需求去写
        return ${aggregate?uncap_first}EntryRepository.findAll();
    }

    /**
     * @explain: 查询 分页 ${classInfo.modelName}
     * @param:   ${classInfo.modelName}SearchRequest 查询条件
     * @param:   pageIndex  第几页
     * @param:   pageSize  每页显示多少条
     */
    @Override
    public Page<${className}Entry> findByPageRequest(${className}SearchRequest request) {

        PageRequest page = request.page();
        return ${aggregate?uncap_first}EntryRepository.findAll(page);

    }


    public void deleteById(Long id) {
        ${aggregate?uncap_first}EntryRepository.deleteById(id);
    }

    public void replay() {

        Optional<TrackingEventProcessor> ret =
            epc.eventProcessor("${aggregate?uncap_first}-processor", TrackingEventProcessor.class);

        if (ret.isPresent()) {

            ${aggregate?uncap_first}EntryRepository.deleteAll();

            TrackingEventProcessor proc = ret.get();

            log.info("{}: supportsReset: {}", proc.getName(), proc.supportsReset());
            log.info("{}: isRunning: {}", proc.getName(), proc.isRunning());
            log.info("{}: processingStatus: {}", proc.getName(), proc.processingStatus().values());

            proc.shutDown();
            proc.resetTokens();
            proc.start();
        } else {
            throw new ValidationException("Process not found.");
        }
    }
}
