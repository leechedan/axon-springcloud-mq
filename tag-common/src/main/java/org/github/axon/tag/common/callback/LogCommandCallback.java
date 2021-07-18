package org.github.axon.tag.common.callback;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 日志回调
 *
 * @author lee
 * @create 2018-02-26 下午5:57
 **/
@Slf4j
public class LogCommandCallback implements CommandCallback {

    @Autowired
    private CommandGateway commandGateway;

    /*@Override
    public void onSuccess(CommandMessage commandMessage, Object result) {
        log.info("请求成功");
    }

    @Override
    public void onFailure(CommandMessage commandMessage, Throwable cause) {
        log.error(commandMessage.toString(), cause);
        if (cause instanceof BusinessException) {
            throw (BusinessException) cause;
        } else {
            throw new BusinessException(cause.toString());
        }
    }*/

    @Override
    public void onResult(CommandMessage commandMessage, CommandResultMessage commandResultMessage) {

    }
}
