package org.github.axon.tag.common.config;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.github.axon.tag.common.continuance.common.SameEventUpcaster;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class AppEventUpCaster extends SingleEventUpcaster {

    private List<SameEventUpcaster> upCasters = Arrays.asList();

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

    public AppEventUpCaster(List<SameEventUpcaster> upCasters){
        if(CollectionUtil.isNotEmpty(upCasters)) {
            this.upCasters = upCasters;
        }

    }

}
