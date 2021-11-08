package ${packageName}.${(moduleName)!}${(subPkgName)!};

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import javax.validation.constraints.NotBlank;
import ${(basePkgName)!}.AbstractCommand;

/**
 * @author ${author}
 * @date ${date}
 */
@Data
@NoArgsConstructor
public class ${aggregate?cap_first}RemoveCommand extends AbstractCommand {

    public ${aggregate?cap_first}RemoveCommand(Long identifier) {
        super(identifier);
    }
}
