package com.trade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.trade.dto.StatusDto;
import com.trade.dto.TradeDetail;
import com.trade.exception.TradeNotFound;
import com.trade.mapper.TradeMapper;
import com.trade.model.Trade;
import com.trade.repo.TradeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradeHistoryService {
    private final TradeRepository tradeRepository;

    public TradeHistoryService (TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public TradeDetail getTradeDetails (String tradeId) {
        Trade trade = getTradeById(tradeId);
        return TradeMapper.map(trade);
    }

    public StatusDto getTradeStatus (String tradeId) {
        Trade trade = getTradeById(tradeId);
        return StatusDto.builder().status(trade.getStatus()).build();
    }

    public Trade getTradeById (String tradeId) {
        Optional<Trade> trade = tradeRepository.findById(tradeId);
        if (trade.isPresent()) {
            return trade.get();
        }
        log.error("Trade with id {} not found", tradeId);
        throw new TradeNotFound(tradeId);
    }

    public List<TradeDetail> geAllTrades () {
        List<Trade> trades = tradeRepository.findAll();
        return new ArrayList<>(TradeMapper.map(trades));
    }

}
