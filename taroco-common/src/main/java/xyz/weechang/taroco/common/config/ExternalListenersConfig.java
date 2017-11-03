package xyz.weechang.taroco.common.config;

import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import xyz.weechang.taroco.common.websocket.ExecutedTradesBroadcaster;

@Configuration
@ComponentScan("org.axonframework.samples.trader.listener")
@PropertySource("classpath:external-config.properties")
public class ExternalListenersConfig {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private ExecutedTradesBroadcaster executedTradesBroadcaster;

    @Bean
    public EventProcessor externalListenersEventProcessor() {
        SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor("externalListenersEventProcessor",
                new SimpleEventHandlerInvoker(executedTradesBroadcaster),
                eventStore);

        eventProcessor.start();

        return eventProcessor;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        return configurer;
    }
}