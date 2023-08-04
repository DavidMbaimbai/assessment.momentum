package com.assessment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalEvent {
    private Long investorId;
    private Long productId;
    private BigDecimal withdrawalAmount;
}
