package org.github.axon.tag.api.domain.account.event;


public class ActivationExpiredEvent {

	private final String accountId;

	public ActivationExpiredEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
