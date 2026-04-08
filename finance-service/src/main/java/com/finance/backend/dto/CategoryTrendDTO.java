package com.finance.backend.dto;

import java.math.BigDecimal;
import com.finance.backend.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTrendDTO {
    private String category;
    private TransactionType type;
    private BigDecimal amount;
}
