package org.github.axon.tag.user.controller;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.axonframework.queryhandling.QueryGateway;
import org.github.axon.tag.api.domain.contract.command.CreateContractCommand;
import org.github.axon.tag.api.domain.transfer.command.RequestTransferCommand;
import org.github.axon.tag.api.domain.transfer.query.QueryTransferCommand;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.github.axon.tag.common.repository.CustomSagaRepository;
import org.github.axon.tag.user.domain.user.BankTransferAggregate;
import org.github.axon.tag.user.service.BankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sagas")
@AllArgsConstructor
public class SagaController {

    private final CommandGateway userCommandGateway;

    private final QueryGateway queryGateway;

    private CustomSagaRepository customSagaRepository;

    @Autowired
    private UIDGenerator uidGenerator;

    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/tran")
    public Object tran(@RequestBody RequestTransferCommand cmd) {
        cmd.setIdentifier(uidGenerator.getId());
        return userCommandGateway.sendAndWait(cmd);
    }

    @GetMapping("/tran/{id}")
    public BankTransferAggregate getUser(@PathVariable("id") String id) {
        QueryTransferCommand command = new QueryTransferCommand(id, Instant.now());

        return queryGateway.query(command, BankTransferAggregate.class).join();
    }

    @GetMapping("/sagaEntry/{id}")
    public List<SagaEntry> getEvents(@PathVariable("id") String id) {

        return customSagaRepository
                .findBySagaId(id);
    }


    @PostMapping("contract")
    public Object test(@RequestBody CreateContractCommand cmd) {
        cmd.setIdentifier(uidGenerator.getId());
        return userCommandGateway.sendAndWait(cmd);
    }


    @RequestMapping("/hello")
    public List<String> hello(@RequestParam(value = "key", required = false) String name) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Map<String, String> map = new HashMap<>();
        for (String beanName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanName);
            String className = bean.getClass().getName();
            if (className.indexOf("org.github.axon") != -1 || className.indexOf("org.axonframework") != -1) {
                map.put(beanName, className);
            }
        }
        List<String> collect = map.entrySet().stream().map(i -> i.getValue()).sorted(Comparator.naturalOrder())
                                  .collect(Collectors.toList());
        return collect;
    }
}
