package org.github.axon.tag.user.domain.user.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.modelling.command.LockAwareAggregate;
import org.github.axon.tag.base.domain.common.AbstractEvent;
import org.github.axon.tag.common.continuance.common.CustomEventSourcingRepository;
import org.github.axon.tag.user.domain.user.UserAggregate;
import org.github.axon.tag.user.entity.UserView;
import org.github.axon.tag.user.entity.UserViewRepository;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 对于抽象事件进行监听
 */
@Slf4j
@AllArgsConstructor
public class UserViewHandler {

    private final CustomEventSourcingRepository<UserAggregate> customEventSourcingRepository;

    private final UserViewRepository userViewRepository;


    /**
     * 任何 user 事件发生之后，重新计算 aggregate 的最新状态，转换成 view 之后存储到本地
     *
     * @param event   any event from user
     * @param message domain event wrapper
     */
    @EventHandler
    public void on(AbstractEvent event, DomainEventMessage<AbstractEvent> message) {


        log.info(MessageFormat.format("{0}: {1} , seq: {2}, payload: {3}",
                                      message.getType(),
                                      message.getAggregateIdentifier(),
                                      message.getSequenceNumber(),
                                      message.getPayload()));

        updateUserView(message.getAggregateIdentifier());
    }

    public void updateUserView(String id) {
        LockAwareAggregate<UserAggregate, EventSourcedAggregate<UserAggregate>> lockAwareAggregate = customEventSourcingRepository
                .load(id);
        UserAggregate aggregate = lockAwareAggregate.getWrappedAggregate().getAggregateRoot();


        UserView userView = userViewRepository.findById(aggregate.getAccountId()).orElse(new UserView());
        userView.setId(aggregate.getAccountId());
        userView.setDeleted(aggregate.getDeleted());
        userView.setName(aggregate.getAccountHolderName());
        userViewRepository.save(userView);
    }
}
