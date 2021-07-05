package org.github.axon.tag.user.entity;

import org.github.axon.tag.user.entity.BankTransferEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferRepository extends JpaRepository<BankTransferEntry, Long> {
}
