package org.github.axon.tag.contract.controller;

import org.github.axon.tag.api.domain.account.command.InitiateMoneyTransactionCommand;
import org.github.axon.tag.api.domain.contract.command.CreateContractCommand;
import org.github.axon.tag.api.domain.transfer.command.RequestTransferCommand;
import org.github.axon.tag.common.continuance.common.CustomDomainEventEntry;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.github.axon.tag.common.repository.CustomDomainEventEntryRepository;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sagas")
@AllArgsConstructor
public class SagaController {

    private final CommandGateway userCommandGateway;

    private final QueryGateway queryGateway;

    @Autowired
    UIDGenerator uidGenerator;

    @Autowired
    private CustomDomainEventEntryRepository customDomainEventEntryRepository;

    @PostMapping("/tran")
    public Object tran(@RequestBody RequestTransferCommand cmd) {
        cmd.setIdentifier(uidGenerator.getId());
        return userCommandGateway.sendAndWait(cmd);
    }

    @GetMapping("/event/{id}")
    public List<CustomDomainEventEntry> getEvents(@PathVariable("id") String id) {

        return customDomainEventEntryRepository
                .findByAggregateIdentifier(id);
    }

    @PostMapping("contract")
    public Object test(@RequestBody CreateContractCommand cmd){
        if (null == cmd.getIdentifier()) {
            cmd.setIdentifier(uidGenerator.getId());
        }
        return userCommandGateway.sendAndWait(cmd);
    }

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping("/hello")
    public  List<String> hello(@RequestParam(value="key", required=false) String name) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Map<String, String> map = new HashMap<>();
        for(String beanName:beanDefinitionNames){
            Object bean = applicationContext.getBean(beanName);
            String className = bean.getClass().getName();
            if(className.indexOf("org.github.axon")!= -1 || className.indexOf("org.axonframework")!=-1){
                map.put(beanName, className);
            }
        }
        List<String> collect = map.entrySet().stream().map(i -> i.getValue()).sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return collect;
    }

}
