package org.github.axon.tag.api.domain.activation;

import org.github.axon.tag.base.domain.common.AbstractCommand;

public class ExpireActivationCommand extends AbstractCommand {

	public ExpireActivationCommand(Long accountId) {
		super(accountId);
	}
}
