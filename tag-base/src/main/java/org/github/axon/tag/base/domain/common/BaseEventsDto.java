package org.github.axon.tag.base.domain.common;

import lombok.Builder;

import java.time.Instant;

@Builder
public class BaseEventsDto {

    private String name;

    private String payload;

    private Long sequenceNumber;

    private Instant timestamp;
}
