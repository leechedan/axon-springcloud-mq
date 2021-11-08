package ${packageName}.${(moduleName)!}${(subPkgName)!};

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import ${(basePkgName)!}.AbstractEvent;

/**
 * @author ${author}
 * @date ${date}
 */
@Data
@NoArgsConstructor
public class ${aggregate?cap_first}RemovedEvent extends AbstractEvent {

    public ${aggregate?cap_first}RemovedEvent(Long identifier) {
        super(identifier);
    }
}
