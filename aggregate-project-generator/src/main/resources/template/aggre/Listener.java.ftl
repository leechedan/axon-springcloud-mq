package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${createdEventPkgName}.${aggregate?cap_first}CreatedEvent;
import ${removedEventPkgName}.${aggregate?cap_first}RemovedEvent;
import ${updatedEventPkgName}.${aggregate?cap_first}UpdatedEvent;
import ${entityPkgName}.${aggregate?cap_first}Entry;
import ${iEventPkgName}.I${aggregate?cap_first}Event;
import ${entryRepositoryPkgName}.${aggregate?cap_first}EntryRepository;
import ${commonPkgName}.exception.BusinessError;
import ${commonPkgName}.exception.BusinessException;

import org.axonframework.messaging.MetaData;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${classInfo.classComment}Handler持久化落地处理器
 * 查询端处理器
 * @author ${author}
 * @date ${date}
 */
@Component
@ProcessingGroup("${aggregate?uncap_first}-processor")
public class ${aggregate?cap_first}Listener implements I${aggregate?cap_first}Event {

    @Autowired
    private ${aggregate?cap_first}EntryRepository repository;

    @Autowired
    private EventProcessingConfiguration epc;

    @Override
    @EventHandler
    public void on(${aggregate?cap_first}CreatedEvent event, MetaData metaData) {
        ${aggregate?cap_first}Entry entry = new ${aggregate?cap_first}Entry();
        BeanUtils.copyProperties(event, entry);
        entry.applyMetaData(metaData);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(${aggregate?cap_first}UpdatedEvent event, MetaData metaData) {
        ${aggregate?cap_first}Entry entry = repository.findById(event.getId())
            .orElseThrow(()->new BusinessException(BusinessError.BU_4004));
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(${aggregate?cap_first}RemovedEvent event, MetaData metaData) {
        repository.deleteById(event.getId());
    }

}
