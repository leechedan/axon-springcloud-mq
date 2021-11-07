package org.github.axon.tag.common.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.github.axon.tag.common.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
public class WorkerId {

    @Id
    @GeneratedValue
    @Field("_id")
    private Long id;

    @Indexed(name = "serviceKey", dropDups = true)
    @Field(name = "service_key")
    private String serviceKey;
}
