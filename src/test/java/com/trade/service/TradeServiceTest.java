package com.trade.service;

import com.broker.external.ExternalBroker;
import com.trade.dto.TradeRequest;
import com.trade.repo.TradeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

 import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private ExternalBroker externalBroker;
    @InjectMocks
    private TradeService tradeService;

    @Before
    public void setUp(){
        doReturn(null).when(tradeRepository).save(any());
        doNothing().when(externalBroker).execute(any());
    }
    @Test
    public void testExecuteBuyOrder () {
        String tradeId = tradeService.executeBuyOrder(TradeRequest.builder()
                                                                  .price(new BigDecimal(200))
                                                                  .quantity(200L)
                                                                  .symbol("USD/JPY")
                                                                  .build());

        assertThat(tradeId).isNotNull();
    }

    @Test
    public void testExecuteSellOrder () {

        String tradeId = tradeService.executeSellOrder(TradeRequest.builder()
            .price(new BigDecimal(9000))
            .quantity(400L)
            .symbol("USD/JPY")
            .build());

        assertThat(tradeId).isNotNull();
    }
}
