package org.github.axon.tag.contract.controller;

import org.github.axon.tag.common.continuance.common.CustomDomainEventEntry;
import org.github.axon.tag.common.repository.CustomDomainEventEntryRepository;
import org.github.axon.tag.contract.domain.contract.ContractAggregate;
import org.github.axon.tag.api.domain.contract.command.CreateContractCommand;
import org.github.axon.tag.api.domain.contract.command.DeleteContractCommand;
import org.github.axon.tag.api.domain.contract.command.QueryContractCommand;
import org.github.axon.tag.api.domain.contract.command.UpdateContractCommand;
import org.github.axon.tag.contract.entity.BankTransferEntry;
import org.github.axon.tag.contract.service.BankTransferService;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/contracts")
@AllArgsConstructor
public class ContractController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    private CustomDomainEventEntryRepository customDomainEventEntryRepository;

    @PostMapping
    public Long createContract(@RequestBody @Valid CreateContractCommand command) {
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{id}")
    public void updateContract(@PathVariable("id") Long id, @RequestBody @Valid UpdateContractCommand command) {
        command.setId(id);
        commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable("id") Long id) {
        commandGateway.sendAndWait(new DeleteContractCommand(id));
    }

    @GetMapping("/{id}")
    public ContractAggregate getContract(@PathVariable("id") String id) {
        QueryContractCommand command = new QueryContractCommand(id, Instant.now());

        return queryGateway.query(command, ContractAggregate.class).join();
    }

    @GetMapping("/event/{id}")
    public List<CustomDomainEventEntry> getEvents(@PathVariable("id") String id) {

        return customDomainEventEntryRepository
                .findByAggregateIdentifier(id);
    }

    @Autowired
    private BankTransferService bankTransferService;

    @GetMapping("trans")
    public List<BankTransferEntry> all(){
        return bankTransferService.findAll();
    }

    @GetMapping("tranid/{id}")
    public BankTransferEntry tran(@PathVariable Long id){
        return bankTransferService.findById(id);
    }

    @GetMapping("tran/replay")
    public void replay(){
        bankTransferService.replay();
    }
}