package org.github.axon.tag.user.controller;

import org.github.axon.tag.api.domain.account.command.BalanceCorrectionCommand;
import org.github.axon.tag.api.domain.account.command.CreateAccountCommand;
import org.github.axon.tag.api.domain.account.command.WithdrawMoneyCommand;
import org.github.axon.tag.api.domain.account.query.QueryUserCommand;
import org.github.axon.tag.common.continuance.common.CustomDomainEventEntry;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.github.axon.tag.common.repository.CustomDomainEventEntryRepository;
import org.github.axon.tag.user.domain.user.UserAggregate;
import org.github.axon.tag.user.domain.user.listener.UserListener;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final CommandGateway userCommandGateway;

    private final QueryGateway queryGateway;

    private UserListener userListener;

    @Autowired
    private CustomDomainEventEntryRepository customDomainEventEntryRepository;

    @Autowired
    private UIDGenerator uidGenerator;

    @PostMapping
    public Object createUser(@RequestBody @Valid CreateAccountCommand command) {
        return userCommandGateway.sendAndWait(command);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody @Valid BalanceCorrectionCommand command) {
        command.setTransactionId(uidGenerator.getId());
        userCommandGateway.sendAndWait(command);
    }

    @PutMapping("/{id}/{amount}")
    public void updateUser(@PathVariable("amount")BigDecimal amount, @PathVariable("id")Long id) {
        WithdrawMoneyCommand cmd = new WithdrawMoneyCommand(id, uidGenerator.getId(), amount);
        userCommandGateway.sendAndWait(cmd);
    }

    @GetMapping("/{id}")
    public UserAggregate getUser(@PathVariable("id") String id) {
        QueryUserCommand command = new QueryUserCommand(id, Instant.now());

        return queryGateway.query(command, UserAggregate.class).join();
    }

    @GetMapping("/event/{id}")
    public List<CustomDomainEventEntry> getEvents(@PathVariable("id") String id) {

        return customDomainEventEntryRepository
                .findByAggregateIdentifier(id);
    }

    @GetMapping("/replay")
    public void replay(){
        userListener.on();
    }

}
