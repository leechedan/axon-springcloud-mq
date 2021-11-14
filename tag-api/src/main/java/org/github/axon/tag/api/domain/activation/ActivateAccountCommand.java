package org.github.axon.tag.api.domain.activation;

import org.github.axon.tag.base.domain.common.AbstractCommand;

public class ActivateAccountCommand extends AbstractCommand {

	public ActivateAccountCommand(Long accountId) {
		super(accountId);
	}
}
