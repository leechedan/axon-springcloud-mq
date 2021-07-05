package org.github.axon.tag.common.web;

import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.github.axon.tag.common.gateway.MetaDataGateway;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lee
 * @date 2020/1/2 14:26
 */
@Slf4j
public class BaseController {

    @Autowired
    protected MetaDataGateway metaDataGateway;

    @Autowired
    protected HttpServletRequest request;

    protected <T> T send(Object command) {
        return (T) metaDataGateway.send(command, MetaData.emptyInstance());
    }

    protected <R> R sendAndWait(Object command) {
        try {
            return metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            Thread.currentThread().interrupt();
            throw new BusinessException(BusinessError.BU_5001);
        }
    }
}
