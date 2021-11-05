package org.github.axon.tag.base.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;

/**
 * @author lee
 * @date 2020/3/20 15:40
 */
@Slf4j
public class BaseHandler {
    protected void checkAuthorization(Object o, MetaData metaData) {
        log.debug("检查权限", o, metaData);
    }
}
