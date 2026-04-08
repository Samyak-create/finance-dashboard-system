package com.finance.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyTrendDTO {
    private Integer year;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal savings;
}
