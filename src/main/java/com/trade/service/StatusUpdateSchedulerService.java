package com.trade.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trade.model.Status;
import com.trade.model.Trade;
import com.trade.repo.TradeRepository;

import lombok.extern.slf4j.Slf4j;

@EnableAsync
@Slf4j
@Service
public class StatusUpdateSchedulerService {

    private final TradeRepository tradeRepository;

    public StatusUpdateSchedulerService (TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Async
    @Scheduled(fixedRate = 5000)
    void checkAndUpdateTradeStatus () {
        log.error("Trade status scheduler started");
        //This is very naive way of updating status, in some sophisticated database. I would go for an update query with  PENDING_EXECUTION and 60 secs  conditions.
        List<Trade> trades = tradeRepository.findAll();
        List<Trade> expiredTrades = trades.stream()
                                          .filter(trade -> Status.PENDING_EXECUTION.value().equals(trade.getStatus()))
                                          .filter(trade -> LocalDateTime.now().isAfter(trade.getTimestamp().plusSeconds(60)))
                                          .map(this::updateStatusAndReason)
                                          .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(expiredTrades)) {
            tradeRepository.saveAll(expiredTrades);
        }
    }

    private Trade updateStatusAndReason (Trade trade) {
        log.info("Trade with id {} got expired", trade.getId());
        trade.setReason("trade expired");
        trade.setStatus(Status.NOT_EXECUTED.value());
        return trade;
    }
}
