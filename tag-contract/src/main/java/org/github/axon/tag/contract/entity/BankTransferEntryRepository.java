package org.github.axon.tag.contract.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferEntryRepository extends JpaRepository<BankTransferEntry, Long> {

}
