package org.github.axon.tag.common.autoconfig;

import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.EventUpcaster;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.github.axon.tag.common.continuance.common.SaveMongoEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@Configuration
@EnableConfigurationProperties(value = {MongoProperties.class})
@AutoConfigureBefore(AxonAutoConfiguration.class)
@EnableMongoRepositories("org.github.axon.tag.common.repository")
@EnableMongoAuditing
public class MongoAxonAutoConfiguration {

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private MongoClient mongo;

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public EventStorageEngine eventStorageEngine(Serializer defaultSerializer, MongoTemplate mongoTemplate,
                                                 @Qualifier("eventSerializer") Serializer eventSerializer,
                                                 EventUpcaster userUpCaster) {
        return MongoEventStorageEngine.builder().mongoTemplate(mongoTemplate)
                                      .snapshotSerializer(defaultSerializer)
                                      .upcasterChain(userUpCaster)
                                      .eventSerializer(eventSerializer)
                                      .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoTemplate axonMongoTemplate(){
        return DefaultMongoTemplate.builder().mongoDatabase(mongo, mongoProperties.getDatabase())
                                   .snapshotEventsCollectionName("snapshotEvents").sagasCollectionName("sagaEntry")
                                   .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore getMongoTokenStore(MongoTemplate mongoTemplate, Serializer serializer){
        return  MongoTokenStore.builder().mongoTemplate(mongoTemplate).serializer(serializer).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SaveMongoEventListener saveMongoEventListener() {
        return new SaveMongoEventListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public SagaStore mongoSagaStore(MongoTemplate mongoTemplate, Serializer serializer) {
        return MongoSagaStore.builder().mongoTemplate(mongoTemplate).serializer(serializer).build();
    }

}
