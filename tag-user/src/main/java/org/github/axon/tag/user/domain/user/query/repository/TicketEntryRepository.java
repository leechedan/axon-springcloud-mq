package org.github.axon.tag.user.domain.user.query.repository;

import org.github.axon.tag.user.domain.user.query.entry.TicketEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 持久化仓库
 *
 * @author lijc
 * @date 2021-06-30
 */
@Repository
public interface TicketEntryRepository extends
        MongoRepository<TicketEntry, String>
        /**QuerydslPredicateExecutor<TicketEntry>*/
{

}
