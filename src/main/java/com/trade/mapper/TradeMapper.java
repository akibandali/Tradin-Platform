package com.trade.mapper;

import static com.trade.service.TradeService.BUY;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.broker.external.BrokerTrade;
import com.broker.external.BrokerTradeSide;
import com.trade.dto.TradeDetail;
import com.trade.model.Trade;

public class TradeMapper {

    public static TradeDetail map (Trade trade) {
        return TradeDetail.builder()
                          .id(trade.getId())
                          .status(trade.getStatus())
                          .timestamp(trade.getTimestamp())
                          .price(trade.getPrice())
                          .quantity(trade.getQuantity())
                          .symbol(trade.getSymbol())
                          .side(trade.getSide())
                          .reason(trade.getReason())
                          .build();
    }

    public static List<TradeDetail> map (List<Trade> trades) {
        return trades.stream().map(TradeMapper::map).collect(Collectors.toList());
    }

    public static BrokerTrade mapToBrokerTrade (Trade trade) {
        BrokerTradeSide tradeSide = trade.getSide().equals(BUY) ? BrokerTradeSide.BUY : BrokerTradeSide.SELL;
        return new BrokerTrade(UUID.fromString(trade.getId()), trade.getSymbol(), trade.getQuantity(), tradeSide, trade.getPrice());
    }

}
