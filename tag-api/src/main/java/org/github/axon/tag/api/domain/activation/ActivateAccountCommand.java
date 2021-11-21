package org.github.axon.tag.api.domain.activation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.github.axon.tag.base.domain.common.AbstractCommand;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivateAccountCommand implements AbstractCommand {

	private Long id;

}
