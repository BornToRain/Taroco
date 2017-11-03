package xyz.weechang.taroco.common.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongodb")
public class TarocoInfrastructureMongoDBConfig {

    @Autowired
    private MongoTemplate eventStoreMongoTemplate;

    @Autowired
    private org.axonframework.mongo.eventhandling.saga.repository.MongoTemplate sagaMongoTemplate;

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(eventStorageEngine());
    }

    @Bean
    public MongoEventStorageEngine eventStorageEngine() {
        return new MongoEventStorageEngine(eventStoreMongoTemplate);
    }

    @Bean
    public MongoSagaStore sagaRepository() {
        return new MongoSagaStore(sagaMongoTemplate);
    }
}