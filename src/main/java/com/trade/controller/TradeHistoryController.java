package com.trade.controller;

import com.trade.dto.StatusDto;
import com.trade.dto.TradeDetail;
import com.trade.service.TradeHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/trades")
public class TradeHistoryController {

    private final TradeHistoryService tradeHistoryService;

    public TradeHistoryController (TradeHistoryService tradeHistoryService) {
        this.tradeHistoryService = tradeHistoryService;
    }

    @GetMapping(path = "{tradeId}/status", produces = "application/json")
    public ResponseEntity<StatusDto> getTradeStatus (@PathVariable String tradeId) {
        StatusDto status = tradeHistoryService.getTradeStatus(tradeId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


    @GetMapping(path = "{tradeId}", produces = "application/json")
    public ResponseEntity<TradeDetail> getTradeDetails (@PathVariable String tradeId) {
        TradeDetail tradeDetails = tradeHistoryService.getTradeDetails(tradeId);
        return new ResponseEntity<>(tradeDetails, HttpStatus.OK);
    }


    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<TradeDetail>> getAllTrades() {
        List<TradeDetail> trades = tradeHistoryService.geAllTrades();
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }
}
