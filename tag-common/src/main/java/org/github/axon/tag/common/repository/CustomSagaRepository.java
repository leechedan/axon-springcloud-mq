
package org.github.axon.tag.common.repository;

import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomSagaRepository extends MongoRepository<SagaEntry, String> {

    List<SagaEntry> findBySagaId(String id);

    List<SagaEntry> findByRevisionAndSagaType(String revision, String type);
}

