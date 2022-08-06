package com.trade.broker;

import com.broker.external.BrokerResponseCallback;
import com.trade.exception.TradeNotFound;
import com.trade.model.Status;
import com.trade.model.Trade;
import com.trade.repo.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class BrokerResponseImpl implements BrokerResponseCallback {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public void successful (UUID tradeId) {
        log.info("successful trade with tradeId : {}", tradeId.toString());
        updateTradeStatus(tradeId.toString(), null, Status.EXECUTED.value());
    }

    @Override
    public void unsuccessful (UUID tradeId, String reason) {
        log.info("unsuccessful trade with tradeId : {}", tradeId.toString());
        updateTradeStatus(tradeId.toString(), reason, Status.NOT_EXECUTED.value());
    }

    public void updateTradeStatus (String tradeId, String reason, String status) {
        Optional<Trade> trade = tradeRepository.findById(tradeId);
        if (trade.isEmpty()) {
            log.error("Trade with id {} not found",tradeId);
            throw new TradeNotFound(tradeId);
        }
        trade.get().setReason(reason);
        trade.get().setStatus(status);
        log.info("changing the status of trade with tradeId : {} to {}", tradeId, status);
        tradeRepository.save(trade.get());
    }
}
