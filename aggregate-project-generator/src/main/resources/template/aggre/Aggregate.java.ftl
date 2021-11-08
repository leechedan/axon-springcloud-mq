package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${createCommandPkgName}.${aggregate?cap_first}CreateCommand;
import ${removeCommandPkgName}.${aggregate?cap_first}RemoveCommand;
import ${updateCommandPkgName}.${aggregate?cap_first}UpdateCommand;
import ${createdEventPkgName}.${aggregate?cap_first}CreatedEvent;
import ${removedEventPkgName}.${aggregate?cap_first}RemovedEvent;
import ${updatedEventPkgName}.${aggregate?cap_first}UpdatedEvent;
import ${iCommandPkgName}.I${aggregate?cap_first}Command;
import ${iEventPkgName}.I${aggregate?cap_first}Event;
import ${(basePkgName)!}.BaseAggregate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.RoutingKey;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.MetaData;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

/**
 * ${classInfo.classComment}聚合根
 * @author ${author}
 * @date ${date}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
@Aggregate(snapshotTriggerDefinition = "snapshotTriggerDefinition")
public class ${aggregate?cap_first}Aggregate extends BaseAggregate
    implements I${aggregate?cap_first}Command, I${aggregate?cap_first}Event {

    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem ><#if !fieldItem.isEntityField>
    /**
     * ${fieldItem.columnName}  ${fieldItem.fieldComment}
     */
    <#if !fieldItem.isPrimaryKey>
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};</#if>
    </#if></#list></#if>

    @CommandHandler
    public ${aggregate?cap_first}Aggregate(${aggregate?cap_first}CreateCommand command, MetaData metaData) {

        ${aggregate?cap_first}CreatedEvent event = new ${aggregate?cap_first}CreatedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    @Override
    @CommandHandler
    public void handle(${aggregate?cap_first}UpdateCommand command, MetaData metaData) {
        ${aggregate?cap_first}UpdatedEvent event = new ${aggregate?cap_first}UpdatedEvent();
        BeanUtils.copyProperties(this, event);
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    @Override
    @CommandHandler
    public void remove(${aggregate?cap_first}RemoveCommand command, MetaData metaData) {
        ${aggregate?cap_first}RemovedEvent event = new ${aggregate?cap_first}RemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    // ----------------------------------------------------

    @Override
    @EventSourcingHandler
    public void on(${aggregate?cap_first}CreatedEvent event, MetaData metaData) {
        applyMetaData(metaData);
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(${aggregate?cap_first}UpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(${aggregate?cap_first}RemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
