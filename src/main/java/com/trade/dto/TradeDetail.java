package com.trade.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TradeDetail {

    @Id
    private String id;
    private Long quantity;
    private String symbol;
    private String side;
    private BigDecimal price;
    private String status;
    private String reason;
    private LocalDateTime timestamp;

}
