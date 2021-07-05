package org.github.axon.tag.api.domain.transfer.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferCompletedEvent {

    private Long  identifier;

}
