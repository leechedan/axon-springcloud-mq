package org.github.axon.tag.api.domain.account.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserCommand {

    String id;
    Instant instant;
}
