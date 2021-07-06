package org.github.axon.tag.contract.service.impl;

import org.github.axon.tag.contract.entity.BankTransferEntry;
import org.github.axon.tag.contract.entity.BankTransferEntryRepository;
import org.github.axon.tag.contract.service.BankTransferService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankTransferServiceImpl implements BankTransferService {

    @Autowired
    private BankTransferEntryRepository repository;

    @Autowired
    private EventProcessingConfiguration epc;

    public BankTransferEntry save(BankTransferEntry bankTransfer) {
        return repository.save(bankTransfer);
    }

    public BankTransferEntry findById(Long id) {
        Optional<BankTransferEntry> ret = repository.findById(id);
        return ret.orElseThrow(RuntimeException::new);
    }

    public List<BankTransferEntry> findAll() {
        return repository.findAll();
//        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void replay() {

        Optional<TrackingEventProcessor> ret =
                epc.eventProcessor("org.github.axon.tag.contract.domain.contract.handler", TrackingEventProcessor.class);

        if (ret.isPresent()) {

            repository.deleteAll();

            TrackingEventProcessor proc = ret.get();

            log.info("{}: supportsReset: {}", proc.getName(), proc.supportsReset());
            log.info("{}: isRunning: {}", proc.getName(), proc.isRunning());
            log.info("{}: processingStatus: {}", proc.getName(), proc.processingStatus().values());


            proc.shutDown();
            proc.resetTokens();
            proc.start();

        } else {
            throw new ValidationException("Process not found.");
        }


    }
}
