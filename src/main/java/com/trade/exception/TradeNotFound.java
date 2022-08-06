package com.trade.exception;

public class TradeNotFound extends RuntimeException {
    private static final long serialVersionUID = 2089454849611061079L;

    public TradeNotFound (String tradeId) {
        super(String.format("Trade with tradeId: %s not found", tradeId));

    }
}
