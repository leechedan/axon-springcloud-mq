package org.github.axon.tag.common.repository;

import org.github.axon.tag.common.helper.WorkerId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerIdRepository extends MongoRepository<WorkerId, Long> {

    WorkerId findByServiceKey(String serviceKey);
}
