package org.github.axon.tag.api.domain.account.event;

import lombok.*;
import org.github.axon.tag.api.domain.account.enums.AccountStatus;
import org.github.axon.tag.base.domain.common.AbstractEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountActivatedEvent extends AbstractEvent {

    private Long accountId;

    private AccountStatus status;
}
