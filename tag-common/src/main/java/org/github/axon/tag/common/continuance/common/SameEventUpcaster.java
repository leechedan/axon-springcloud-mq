package org.github.axon.tag.common.continuance.common;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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

//        return outputType(intermediateRepresentation.getType()) != null;
        return eventTypeName().equals(intermediateRepresentation.getType().getName());
    }

    @Override
    public IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.upcast(
            outputType(intermediateRepresentation.getType()), String.class,
//            d -> this.doUpCastPayload(JSONUtil.parseObj(d), intermediateRepresentation),
                d->d,
            metaData -> this.doUpCastMetaData(metaData, intermediateRepresentation)
        );
    }

    public SimpleSerializedType outputType(SerializedType originType) {
        return new SimpleSerializedType(eventTypeName(), outputRevision(originType.getRevision()));
    }

    public abstract String eventTypeName();

    public abstract String outputRevision(String originRevision);

    public abstract String doUpCastPayload(JSONObject document, IntermediateEventRepresentation intermediateEventRepresentation);

    public abstract MetaData doUpCastMetaData(MetaData document, IntermediateEventRepresentation intermediateEventRepresentation);

}
