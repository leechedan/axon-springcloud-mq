package org.github.axon.tag.user.domain.user.saga;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.github.axon.tag.api.domain.account.event.AccountActivatedEvent;
import org.github.axon.tag.api.domain.account.event.AccountCreatedEvent;
import org.github.axon.tag.api.domain.account.event.ActivationExpiredEvent;
import org.github.axon.tag.api.domain.account.event.ActivationRequestedEvent;
import org.github.axon.tag.api.domain.account.exceptions.UnmatchedActivationCodeException;
import org.github.axon.tag.api.domain.activation.ActivateAccountCommand;
import org.github.axon.tag.api.domain.activation.ExpireActivationCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.axonframework.modelling.saga.SagaLifecycle.associateWith;

@Slf4j
//@Saga
@Getter
@Setter
@NoArgsConstructor
//@ProcessingGroup("BankAccountMongoListener")
public class ActivationSaga {

	private static final String ASSOCIATION_PROPERTY_ACCOUNT_ID = "accountId";
	private static final String ASSOCIATION_PROPERTY_PHONE_NUMBER = "phoneNumber";

	private static final String ACTIVATION_DEADLINE = "activation-deadline";
	private static final Duration ACTIVATION_TIMEOUT = Duration.ofSeconds(20);

	private static final Log LOGGER = LogFactory.getLog(ActivationSaga.class);

	@Autowired
	@JsonIgnore
	private transient CommandGateway commandGateway;

	@Autowired
	@JsonIgnore
	private transient DeadlineManager deadlineManager;

	private Long accountId;
	private String activationCode;
	private String activationScheduleId;

	@StartSaga
	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_ACCOUNT_ID)
	public void on(AccountCreatedEvent event) {
		log.info("start saga activatioin {}", event);
		accountId = event.getAccountId();
		String phoneNumber = event.getAccountHolderName();

		associateWith(ASSOCIATION_PROPERTY_PHONE_NUMBER, phoneNumber);
		associateWith("name", phoneNumber);

		activationScheduleId = deadlineManager.schedule(ACTIVATION_TIMEOUT, ACTIVATION_DEADLINE);
		activationCode = generateActivationCode();
		LOGGER.info("Waiting for activation: " + accountId);
	}

	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_PHONE_NUMBER)
	public void on(ActivationRequestedEvent event) {

		if (activationCode.equals(event.getActivationCode())) {
			deadlineManager.cancelSchedule(ACTIVATION_DEADLINE, activationScheduleId);
			commandGateway.sendAndWait(new ActivateAccountCommand(accountId));
		} else {
			throw new UnmatchedActivationCodeException(accountId);
		}
	}

	@EndSaga
	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_ACCOUNT_ID)
	public void on(AccountActivatedEvent event) {
		LOGGER.info("Account activated: " + event.getAccountId());
	}

	@DeadlineHandler(deadlineName = ACTIVATION_DEADLINE)
	public void handleActivationExpiration() {
		log.info("expire {}", accountId);
		commandGateway.sendAndWait(new ExpireActivationCommand(accountId));
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "accountId")
	public void on(ActivationExpiredEvent event) {
		LOGGER.info("Account activation expired: " + event.getAccountId());
	}

	private String generateActivationCode() {
		//return String.valueOf((int) Math.round(Math.random() * 1000));
		return "111";
	}
}
