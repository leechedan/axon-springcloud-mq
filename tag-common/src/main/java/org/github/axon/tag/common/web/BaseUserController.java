package org.github.axon.tag.common.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.messaging.MetaData;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;

/**
 * @author lee
 * @date 2020/1/2 14:26
 */
@Slf4j
public class BaseUserController extends BaseController {

    protected String getToken() {
        String token = request.getHeader("U-Token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(BusinessError.BU_9001);
        }
        return token;
    }


    protected MetaData genMetaData() {
        return MetaData.with("operationTime", System.currentTimeMillis());
    }

    @Override
    protected <T> T send(Object command) {
        return (T) metaDataGateway.send(command, genMetaData());
    }


    @Override
    protected <R> R sendAndWait(Object command) {
        try {
            return metaDataGateway.sendAndWait(command, genMetaData());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            Thread.currentThread().interrupt();
            throw new BusinessException(BusinessError.BU_5001);
        }
    }
}
