package org.github.axon.tag.base.domain.common;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.axonframework.messaging.MetaData;

import java.io.Serializable;

/**
 * @author lee
 * @date 2020/2/24 12:50
 */
@Data
@FieldNameConstants
public class BaseAggregate implements Serializable {

//    public final static String FIELD_ADMIN_ID = "admin_id";
//    public final static String FIELD_USER_ID = "user_id";
    private String adminId;
    private String userId;

    protected void applyMetaData(MetaData metaData) {
//        adminId = String.valueOf(metaData.get(BaseAggregate.FIELD_ADMIN_ID));
//        userId = String.valueOf(metaData.get(BaseAggregate.FIELD_USER_ID));
    }
}
