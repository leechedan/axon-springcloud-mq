package org.github.axon.tag.common.repository;

import org.github.axon.tag.common.helper.WorkerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerIdRepository extends JpaRepository<WorkerId, Long> {

    WorkerId findByServiceKey(String serviceKey);
}
