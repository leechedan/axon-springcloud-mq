package org.github.axon.tag.api.domain.account.exception;

public class DepositNotPermittedException extends Exception {
    public DepositNotPermittedException() {
        super();
    }

    public DepositNotPermittedException(String message) {
        super(message);
    }

    public DepositNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositNotPermittedException(Throwable cause) {
        super(cause);
    }
}
