package org.github.axon.tag.user.domain.user.query.listener;


import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.github.axon.tag.api.domain.ticket.event.ITicketEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketCreatedEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketRemovedEvent;
import org.github.axon.tag.api.domain.ticket.event.TicketUpdatedEvent;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;
import org.github.axon.tag.user.domain.user.query.entry.TicketEntry;
import org.github.axon.tag.user.domain.user.query.repository.TicketEntryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener持久化落地处理器 查询端处理器
 *
 * @author lijc
 * @date 2021-06-30
 */
@Component
public class TicketListener implements ITicketEvent {

    @Autowired
    private TicketEntryRepository repository;

    @Override
    @EventHandler
    public void on(TicketCreatedEvent event, MetaData metaData) {
        TicketEntry entry = new TicketEntry();
        BeanUtils.copyProperties(event, entry);
        entry.applyMetaData(metaData);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(TicketUpdatedEvent event, MetaData metaData) {
        TicketEntry entry = repository.findById(event.getId())
                                      .orElseThrow(() -> new BusinessException(BusinessError.BU_4004));
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(TicketRemovedEvent event, MetaData metaData) {
        repository.deleteById(event.getId());
    }
}
