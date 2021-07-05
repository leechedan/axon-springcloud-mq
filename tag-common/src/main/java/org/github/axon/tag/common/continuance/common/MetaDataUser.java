package org.github.axon.tag.common.continuance.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MetaDataUser implements MetaDataUserInterface {

    private String name;

    private Long userId;

    private Long customerId;
}
