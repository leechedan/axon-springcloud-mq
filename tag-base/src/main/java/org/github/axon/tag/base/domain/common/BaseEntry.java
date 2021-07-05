package org.github.axon.tag.base.domain.common;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.axonframework.messaging.MetaData;

import java.io.Serializable;


@Data
@FieldNameConstants
public class BaseEntry implements Serializable {

    private String adminId;
    private String userId;

    public void applyMetaData(MetaData metaData) {
        adminId = String.valueOf(metaData.get(BaseConstants.FIELD_ADMIN_ID));
        userId = String.valueOf(metaData.get(BaseConstants.FIELD_USER_ID));

    }
}
