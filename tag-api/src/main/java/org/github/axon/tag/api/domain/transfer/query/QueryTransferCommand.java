package org.github.axon.tag.api.domain.transfer.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryTransferCommand {

    String id;
    Instant instant;
}
