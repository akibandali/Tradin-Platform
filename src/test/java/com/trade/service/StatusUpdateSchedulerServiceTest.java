package com.trade.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.trade.model.Status;
import com.trade.model.Trade;

@RunWith(MockitoJUnitRunner.class)
public class StatusUpdateSchedulerServiceTest {

    @InjectMocks
    private StatusUpdateSchedulerService service;

    @Test
    public void testFilterAndUpdateTrades () {
        List<Trade> updatedTrades = service.filterAndUpdateTrades(getTestTrade());
        assertThat(updatedTrades.size()).isEqualTo(1);
        assertThat(updatedTrades.get(0).getId()).isEqualTo("1");
        assertThat(updatedTrades.get(0).getStatus()).isEqualTo(Status.NOT_EXECUTED.value());
        assertThat(updatedTrades.get(0).getReason()).isEqualTo("trade expired");
    }

    private List<Trade> getTestTrade () {
        Trade trade1 = Trade.builder()
                            .id("1")
                            .side("SELL")
                            .status(Status.PENDING_EXECUTION.value())
                            .timestamp(LocalDateTime.now().minusSeconds(120))
                            .build();
        Trade trade2 = Trade.builder()
                            .id("2")
                            .side("BUY")
                            .status(Status.PENDING_EXECUTION.value())
                            .timestamp(LocalDateTime.now().minusSeconds(10))
                            .build();
        Trade trade3 = Trade.builder()
                            .id("3")
                            .side("SELL")
                            .status(Status.EXECUTED.value())
                            .timestamp(LocalDateTime.now().minusSeconds(20))
                            .build();
        Trade trade4 = Trade.builder()
                            .id("4")
                            .side("BUY")
                            .status(Status.NOT_EXECUTED.value())
                            .timestamp(LocalDateTime.now())
                            .build();
        return Arrays.asList(trade1, trade2, trade4);
    }

}
