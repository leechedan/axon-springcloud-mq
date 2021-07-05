package org.github.axon.tag.contract.service;

import org.github.axon.tag.contract.entity.BankTransferEntry;

import java.util.List;

public interface BankTransferService {

    BankTransferEntry save(BankTransferEntry bankTransfer);

    BankTransferEntry findById(Long id);

    List<BankTransferEntry> findAll();

    void deleteById(Long id);

    void replay();
}
