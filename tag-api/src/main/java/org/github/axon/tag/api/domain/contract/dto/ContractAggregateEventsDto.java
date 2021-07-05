package org.github.axon.tag.api.domain.contract.dto;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public class ContractAggregateEventsDto {

    String name;
    String payload;
    Long sequenceNumber;
    Instant timestamp;
}
