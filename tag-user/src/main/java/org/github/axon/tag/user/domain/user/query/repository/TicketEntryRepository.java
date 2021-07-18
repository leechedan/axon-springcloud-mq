package org.github.axon.tag.user.domain.user.query.repository;

import org.github.axon.tag.user.domain.user.query.entry.TicketEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 持久化仓库
 *
 * @author lijc
 * @date 2021-06-30
 */
@Repository
public interface TicketEntryRepository extends
        JpaRepository<TicketEntry, String>
        /**QuerydslPredicateExecutor<TicketEntry>*/
{

}
