package org.github.axon.tag.common.repository;

import org.github.axon.tag.common.continuance.common.CustomDomainEventEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomDomainEventEntryRepository extends JpaRepository<CustomDomainEventEntry, String> {

    /**
     * 查找事件
     *
     * @param identifier
     * @return
     */
    CustomDomainEventEntry findByEventIdentifier(String identifier);

    List<CustomDomainEventEntry> findByAggregateIdentifier(String identifier);
}
