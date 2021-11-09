package ${packageName}.${(moduleName)!}${(subPkgName)!};

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import ${createdEventPkgName}.${aggregate?cap_first}CreatedEvent;
import ${updatedEventPkgName}.${aggregate?cap_first}UpdatedEvent;
import ${(commonPkgName)!}.continuance.common.SameEventUpcaster;

import java.util.HashMap;

public class ${aggregate?cap_first}CreatedEventUpcaster extends SameEventUpcaster {
    /**
    * 关联的useraggregate一定要写对其创建事件  否则 https://discuss.axoniq.io/t/incompatibleaggregateexception-aggregate-identifier-must-be-non-null-after-applying-an-event/1918
    * @return
    */
    @Override
    public String eventTypeName() {
        return ${aggregate?cap_first}CreatedEvent.class.getTypeName();
    }

    @Override
    public String outputRevision(String originRevision) {
        final HashMap<String, String> revisionConvertMpp = new HashMap<>();
        revisionConvertMpp.put(null, "1.0.0");

        return revisionConvertMpp.get(originRevision);
    }

    @Override
    public JsonNode doUpCastPayload(JsonNode document, IntermediateEventRepresentation intermediateEventRepresentation) {

        if (intermediateEventRepresentation.getType().getRevision() == null) {
            ((ObjectNode) document).put("industryName", "互联网");
        }

        return document;
    }

    @Override
    public MetaData doUpCastMetaData(MetaData document,
    IntermediateEventRepresentation intermediateEventRepresentation) {
        return document;
    }
}
