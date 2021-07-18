package org.github.axon.tag.common.repository;

import org.axonframework.modelling.saga.repository.jpa.AssociationValueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomAssociationValueEntryRepository extends JpaRepository<AssociationValueEntry, String> {

    List<AssociationValueEntry> findBySagaId(String id);

    List<AssociationValueEntry> findBySagaType(String type);
}
