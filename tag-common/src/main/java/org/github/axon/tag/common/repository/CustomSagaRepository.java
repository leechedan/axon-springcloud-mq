package org.github.axon.tag.common.repository;

import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomSagaRepository extends JpaRepository<SagaEntry, String> {

    List<SagaEntry> findBySagaId(String id);

    List<SagaEntry> findByRevisionAndSagaType(String revision, String type);
}
