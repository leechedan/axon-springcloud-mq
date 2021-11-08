package ${packageName}.${(moduleName)!}${(subPkgName)!};

import org.axonframework.messaging.MetaData;

import ${createdEventPkgName}.${aggregate?cap_first}CreatedEvent;
import ${removedEventPkgName}.${aggregate?cap_first}RemovedEvent;
import ${updatedEventPkgName}.${aggregate?cap_first}UpdatedEvent;

/**
 * ${classInfo.classComment}监听器接口
 * @author ${author}
 * @date ${date}
 */
public interface I${aggregate?cap_first}Event {

    void on(${aggregate?cap_first}CreatedEvent event, MetaData metaData);

    void on(${aggregate?cap_first}UpdatedEvent event, MetaData metaData);

    void on(${aggregate?cap_first}RemovedEvent event, MetaData metaData);
}
