package org.github.axon.tag.user.domain.user.upcaster;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.github.axon.tag.api.domain.transfer.event.saga.TransferRequestedEvent;
import org.github.axon.tag.common.continuance.common.SameEventUpcaster;

import java.util.HashMap;

@Slf4j
public class TransferRequestedEventUpCaster extends SameEventUpcaster {

    @Override
    public String eventTypeName() {
        return TransferRequestedEvent.class.getTypeName();
    }

    @Override
    public String outputRevision(String originRevision) {
        final HashMap<String, String> revisionConvertMpp = new HashMap<>();
        revisionConvertMpp.put(null, "1.0.0");

        return revisionConvertMpp.get(originRevision);
    }

    @Override
    public JsonNode doUpCastPayload(JsonNode document,
                                    IntermediateEventRepresentation intermediateEventRepresentation) {

        // 每个版本只有一种升级方案，然后链式一步一步升级
        if (intermediateEventRepresentation.getType().getRevision() == null) {
//            ((ObjectNode) document).put("industryName", "互联网");
        }

        log.debug("{}", document);
        return document;
    }

    @Override
    public MetaData doUpCastMetaData(MetaData document,
                                     IntermediateEventRepresentation intermediateEventRepresentation) {
        return document;
    }
}
