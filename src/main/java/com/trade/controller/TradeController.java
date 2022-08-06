package com.trade.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.trade.service.TradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trade.dto.TradeRequest;
import com.trade.exception.BadRequestException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api")
public class TradeController {

    private final TradeService tradeService;

    @Value("${trade.valid.symbols}")
    private String validSymbols;

    public TradeController (TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(path = "/buy", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void>  buyTrade (@RequestBody TradeRequest tradeRequest) {
        validateTradeRequest(tradeRequest);
        String tradeId = tradeService.executeBuyOrder(tradeRequest);
        HttpHeaders httpHeaders = getResponseHttpHeaders(tradeId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping(path = "/sell", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> sellTrade (@RequestBody TradeRequest tradeRequest) {
        validateTradeRequest(tradeRequest);
        String tradeId = tradeService.executeSellOrder(tradeRequest);
        HttpHeaders httpHeaders = getResponseHttpHeaders(tradeId);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    private void validateTradeRequest (TradeRequest tradeRequest) {
        String[] arr = StringUtils.split(validSymbols, ",");
        List<String> validSymbols = Arrays.asList(arr);
        if (tradeRequest.getSymbol() == null || !validSymbols.contains(tradeRequest.getSymbol())) {
            throw new BadRequestException(String.format("Symbol valid values: %s", validSymbols));
        }

        if (tradeRequest.getQuantity() == null || tradeRequest.getQuantity() <= 0 || tradeRequest.getQuantity() > 1000000) {
            throw new BadRequestException("quantity must be greater than 0 and less than or equal to 1M“");
        }

        if (tradeRequest.getPrice() == null || tradeRequest.getPrice().compareTo(new BigDecimal("0.0")) <= 0) {
            throw new BadRequestException("price must be greater than 0");
        }

    }

    private HttpHeaders getResponseHttpHeaders (String tradeId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location = WebMvcLinkBuilder.linkTo(methodOn(TradeHistoryController.class).getTradeStatus(tradeId)).toUri();
        httpHeaders.add("Location", location.toString());
        return httpHeaders;
    }
}
