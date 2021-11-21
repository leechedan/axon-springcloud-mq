package org.github.axon.tag.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.github.axon.tag.user.entity.BankTransferEntry;
import org.github.axon.tag.user.entity.BankTransferRepository;
import org.github.axon.tag.user.service.BankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;

@Service
@Slf4j
public class BankTransferServiceImpl implements BankTransferService {

    @Autowired
    private BankTransferRepository repository;

//    @Autowired
//    private EventProcessingConfiguration epc;

    public BankTransferEntry save(BankTransferEntry bankTransfer) {
        return repository.save(bankTransfer);
    }

    public BankTransferEntry findById(Long id) {
        Optional<BankTransferEntry> ret = repository.findById(id);

        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    public List<BankTransferEntry> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /*public void replay() {

        Optional<TrackingEventProcessor> ret =
                epc.eventProcessor("BankTransferMongoListener", TrackingEventProcessor.class);

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
    }*/
}
