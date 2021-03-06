package xyz.weechang.taroco.common.config;

import com.mongodb.MongoClient;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.net.UnknownHostException;

@Configuration
public class PersistenceInfrastructureConfig {

    @Bean
    @Profile("hsqldb")
    public EmbeddedDatabaseFactoryBean dataSource() {
        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.HSQL);

        return embeddedDatabaseFactoryBean;
    }

    @Bean
    @Profile("mongodb")
    public MongoClient mongo() throws UnknownHostException {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Bean
    @Profile("mongodb")
    public MongoTemplate mongoSpringTemplate() throws UnknownHostException {
        return new MongoTemplate(mongo(), "axontrader");
    }

    @Bean
    @Profile("mongodb")
    public org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate mongoTemplate() throws UnknownHostException {
        return new DefaultMongoTemplate(mongo(), "axontrader", "domainevents", "snapshotevents");
    }

    @Bean
    @Profile("mongodb")
    public org.axonframework.mongo.eventhandling.saga.repository.MongoTemplate mongoSagaTemplate()
            throws UnknownHostException {
        return new org.axonframework.mongo.eventhandling.saga.repository.DefaultMongoTemplate(mongo(),
                                                                                              "axontrader",
                                                                                              "sagas");
    }
}