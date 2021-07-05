package org.github.axon.tag.user.domain.user.upcaster;

import cn.hutool.json.JSONObject;
import org.github.axon.tag.api.domain.ticket.event.TicketCreatedEvent;
import org.github.axon.tag.common.continuance.common.SameEventUpcaster;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;

import java.util.HashMap;

public class TicketCreatedEventUpcaster extends SameEventUpcaster {

    /**
    * 关联的useraggregate一定要写对其创建事件  否则
    * https://discuss.axoniq.io/t/incompatibleaggregateexception-aggregate-identifier-must-be-non-null-after-applying-an-event/1918
    * @return
    */
    @Override
    public String eventTypeName() {
        return TicketCreatedEvent.class.getTypeName();
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
            (document).put("industryName", "互联网");
        }

        return document.toString();
    }

    @Override
    public MetaData doUpCastMetaData(MetaData document, IntermediateEventRepresentation intermediateEventRepresentation) {
        return document;
    }
}
