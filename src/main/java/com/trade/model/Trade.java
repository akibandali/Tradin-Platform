package com.trade.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Trades")
public class Trade {

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
