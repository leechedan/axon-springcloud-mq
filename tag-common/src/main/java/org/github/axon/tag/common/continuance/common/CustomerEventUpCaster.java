package org.github.axon.tag.common.continuance.common;

import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

import java.util.Arrays;
import java.util.List;

public class CustomerEventUpCaster extends SingleEventUpcaster {

    private static List<SameEventUpcaster> upCasters = Arrays.asList(

    );

    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return upCasters.stream().anyMatch(o -> o.canUpcast(intermediateRepresentation));
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        SameEventUpcaster upCaster = upCasters.stream()
                                              .filter(o -> o.canUpcast(intermediateRepresentation))
                                              .findAny().orElseThrow(RuntimeException::new);
        return upCaster.doUpcast(intermediateRepresentation);
    }
}
