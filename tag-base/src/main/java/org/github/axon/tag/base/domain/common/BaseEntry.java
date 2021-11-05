package org.github.axon.tag.base.domain.common;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.axonframework.messaging.MetaData;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@FieldNameConstants
public class BaseEntry implements Serializable {

    @Id
    private Long id;

    private String adminId;

    private String userId;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Long createdBy;

    private Long updatedBy;

    public void applyMetaData(MetaData metaData) {
        adminId = String.valueOf(metaData.get(BaseConstants.FIELD_ADMIN_ID));
        userId = String.valueOf(metaData.get(BaseConstants.FIELD_USER_ID));

    }
}
