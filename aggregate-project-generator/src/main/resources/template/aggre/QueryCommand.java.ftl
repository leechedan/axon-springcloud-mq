package ${packageName}.${(moduleName)!}${(subPkgName)!};

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
* @author ${author}
* @date ${date}
*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ${aggregate?cap_first}QueryCommand {

    @NotBlank
    @NotNull
    private String id;

    private Instant endDate;
}