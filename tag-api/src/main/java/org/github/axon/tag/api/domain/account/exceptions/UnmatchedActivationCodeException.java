package org.github.axon.tag.api.domain.account.exceptions;

public class UnmatchedActivationCodeException extends IllegalStateException {

	private Long accountId;

	public UnmatchedActivationCodeException(Long accountId) {
		super("Wrong activation code entered for account " + accountId);
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}
}
