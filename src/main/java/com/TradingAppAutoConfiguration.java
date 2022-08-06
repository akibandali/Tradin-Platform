package com;

import com.broker.external.BrokerResponseCallback;
import com.broker.external.ExternalBroker;
import com.trade.broker.BrokerResponseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class TradingAppAutoConfiguration {

    @Bean
    public ExternalBroker externalBroker () {
        return new ExternalBroker(brokerResponseCallback());
    }

    @Bean
    public BrokerResponseCallback brokerResponseCallback () {
        return new BrokerResponseImpl();
    }
}
