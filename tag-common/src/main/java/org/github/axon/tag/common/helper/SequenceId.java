package org.github.axon.tag.common.helper;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * ID实体类
 * 存储每个集合的ID记录，记录每个集合的自增ID到了多少
 */
@Data
@Document(collection = "sequence")
public class SequenceId {
    /** 主键 */
    @Id
    private String id;

    /** 自增ID值 */
    @Field("seq_id")
    private long seqId;

    /** 集合名称 */
    @Field("coll_name")
    private String collName;
}