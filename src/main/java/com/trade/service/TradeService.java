package com.trade.service;

import com.broker.external.ExternalBroker;
import com.trade.repo.TradeRepository;
import com.trade.dto.TradeRequest;
import com.trade.model.Status;
import com.trade.model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.trade.mapper.TradeMapper.mapToBrokerTrade;

@Service
@Slf4j
public class TradeService {
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    private final TradeRepository tradeRepository;
    private final ExternalBroker externalBroker;

    public TradeService (TradeRepository tradeRepository, ExternalBroker externalBroker) {
        this.tradeRepository = tradeRepository;
        this.externalBroker = externalBroker;
    }

    public String executeBuyOrder (TradeRequest tradeRequest) {
        log.debug("processing buy order for symbol {}", tradeRequest.getSymbol());
        Trade trade = getTrade(BUY, tradeRequest);
        tradeRepository.save(trade);
        externalBroker.execute(mapToBrokerTrade(trade));
        return trade.getId();
    }

    public String executeSellOrder (TradeRequest tradeRequest) {
        log.debug("processing sell order for symbol {}", tradeRequest.getSymbol());
        Trade trade = getTrade(SELL, tradeRequest);
        tradeRepository.save(trade);
        return trade.getId();
    }

    private Trade getTrade (String side, TradeRequest tradeRequest) {
        UUID tradeId = UUID.randomUUID();
        return Trade.builder()
                    .id(tradeId.toString())
                    .price(tradeRequest.getPrice())
                    .quantity(tradeRequest.getQuantity())
                    .timestamp(LocalDateTime.now())
                    .symbol(tradeRequest.getSymbol())
                    .status(Status.PENDING_EXECUTION.value())
                    .side(side)
                    .build();

    }

}
