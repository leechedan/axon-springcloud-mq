package ${packageName}.${(moduleName)!}${(subPkgName)!};

import ${createCommandPkgName}.${aggregate?cap_first}CreateCommand;
import ${removeCommandPkgName}.${aggregate?cap_first}RemoveCommand;
import ${updateCommandPkgName}.${aggregate?cap_first}UpdateCommand;
import org.axonframework.messaging.MetaData;

/**
 * @author ${author}
 * @date ${date}
 */
public interface I${aggregate?cap_first}Command {

    void handle(${aggregate?cap_first}UpdateCommand command, MetaData metaData);

    //void handle(${aggregate?cap_first}CreateCommand command, MetaData metaData);

    void remove(${aggregate?cap_first}RemoveCommand command, MetaData metaData);
}
