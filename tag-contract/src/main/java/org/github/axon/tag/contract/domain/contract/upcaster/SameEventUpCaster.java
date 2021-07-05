package org.github.axon.tag.contract.domain.contract.upcaster;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.core.instrument.util.JsonUtils;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.SerializedType;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

public abstract class SameEventUpCaster extends SingleEventUpcaster {

    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {

        return outputType(intermediateRepresentation.getType()) != null;
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.upcast(
            outputType(intermediateRepresentation.getType()),
            String.class,
            d -> this.doUpCastPayload(JSONUtil.parseObj(d), intermediateRepresentation),
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
