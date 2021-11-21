package org.github.axon.tag.api.domain.account.event;

import lombok.*;
import org.github.axon.tag.api.domain.account.enums.AccountStatus;
import org.github.axon.tag.base.domain.common.AbstractEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountActivatedEvent implements AbstractEvent {

    private Long accountId;

    private AccountStatus status;

    private Long id;

}
