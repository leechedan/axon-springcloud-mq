package org.github.axon.tag.common.continuance.common;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.SerializedType;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

@Slf4j
public abstract class SameEventUpcaster extends SingleEventUpcaster {

    private Serializer serializer;

    public boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {

        return eventTypeName().equals(intermediateRepresentation.getType().getName());
    }

    @Override
    public IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.upcast(
                outputType(intermediateRepresentation.getType()), JsonNode.class,
                d -> this.doUpCastPayload(d, intermediateRepresentation),
                metaData -> this.doUpCastMetaData(metaData, intermediateRepresentation)
        );
    }

    public SimpleSerializedType outputType(SerializedType originType) {
        return new SimpleSerializedType(eventTypeName(), outputRevision(originType.getRevision()));
    }

    public abstract String eventTypeName();

    public abstract String outputRevision(String originRevision);

    public abstract JsonNode doUpCastPayload(JsonNode document,
                                             IntermediateEventRepresentation intermediateEventRepresentation);

    public abstract MetaData doUpCastMetaData(MetaData document,
                                              IntermediateEventRepresentation intermediateEventRepresentation);
}
