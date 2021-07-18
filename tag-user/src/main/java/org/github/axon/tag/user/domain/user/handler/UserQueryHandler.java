package org.github.axon.tag.user.domain.user.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.modelling.command.LockAwareAggregate;
import org.axonframework.queryhandling.QueryHandler;
import org.github.axon.tag.api.domain.account.query.QueryUserCommand;
import org.github.axon.tag.common.continuance.common.CustomEventSourcingRepository;
import org.github.axon.tag.user.domain.user.UserAggregate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserQueryHandler {

    private final CustomEventSourcingRepository<UserAggregate> userAggregateRepository;

    @QueryHandler
    public UserAggregate on(QueryUserCommand command) {
        LockAwareAggregate<UserAggregate, EventSourcedAggregate<UserAggregate>> lockAwareAggregate = userAggregateRepository
                .load(command.getId().toString(), command.getInstant());
        return lockAwareAggregate.getWrappedAggregate().getAggregateRoot();
    }
}
