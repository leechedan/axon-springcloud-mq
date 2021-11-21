package org.github.axon.tag.user.service;

import org.github.axon.tag.user.entity.BankTransferEntry;

import java.util.List;

public interface BankTransferService {

    BankTransferEntry save(BankTransferEntry BankTransferEntry);

    BankTransferEntry findById(Long id);

    List<BankTransferEntry> findAll();

    void deleteById(Long id);

//    void replay();
}
