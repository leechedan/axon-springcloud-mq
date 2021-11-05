package org.github.axon.tag.common.continuance.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.github.axon.tag.base.domain.common.AbstractCommand;
import org.github.axon.tag.common.helper.UIDGenerator;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@AllArgsConstructor
@Configuration
public class CommandInterceptor implements MessageDispatchInterceptor {

    private final UIDGenerator uidGenerator;

    @Override
    public BiFunction<Integer, GenericCommandMessage<AbstractCommand>, GenericCommandMessage<AbstractCommand>> handle(
            List messages) {
        return (index, message) -> {
            log.info("command interceptor {} {}", index, message);
            // create command 自动生成 ID
            if (message.getPayload() instanceof AbstractCommand) {
                AbstractCommand payload = (AbstractCommand) message.getPayload();
                if (payload.getId() == null) {
                    payload.setId(uidGenerator.getId());
                }
            }

            // 添加 user info 作为 MetaData，由于项目不设计 security 这里就简单的附加一个假的用户
            Map<String, MetaDataUserInterface> map = new HashMap<>();

//            map.put("user", MetaDataUser.builder().customerId(1L).name("Test").userId(2L).build());
            return map.isEmpty() ? message : message.andMetaData(map);
        };
    }
}
