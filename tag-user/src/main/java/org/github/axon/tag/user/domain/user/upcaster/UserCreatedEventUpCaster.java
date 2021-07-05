package org.github.axon.tag.user.domain.user.upcaster;

import cn.hutool.json.JSONObject;
import org.github.axon.tag.api.domain.account.event.AccountCreatedEvent;
import org.github.axon.tag.common.continuance.common.SameEventUpcaster;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;

import java.util.HashMap;

@Slf4j
public class UserCreatedEventUpCaster extends SameEventUpcaster {

    /**
     * 关联的userAggregate一定要正确其创建事件  否则
     * https://discuss.axoniq.io/t/incompatibleaggregateexception-aggregate-identifier-must-be-non-null-after-applying-an-event/1918
     * @return
     */
    @Override
    public String eventTypeName() {
        return AccountCreatedEvent.class.getTypeName();
    }

    @Override
    public String outputRevision(String originRevision) {
        final HashMap<String, String> revisionConvertMpp = new HashMap<>();
        revisionConvertMpp.put(null, "1.0.0");

        return revisionConvertMpp.get(originRevision);
    }

    @Override
    public String doUpCastPayload(JSONObject document, IntermediateEventRepresentation intermediateEventRepresentation) {

        if (intermediateEventRepresentation.getType().getRevision() == null) {
//            ((ObjectNode) document).put("industryName", "互联网");
        }
        if (document.get("accountHolderName")==null){
            (document).put("accountHolderName", "互联网");
        }

        return document.toString();
    }

    @Override
    public MetaData doUpCastMetaData(MetaData document, IntermediateEventRepresentation intermediateEventRepresentation) {
        return document;
    }
}
