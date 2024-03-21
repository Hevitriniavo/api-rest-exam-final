package hei.shool.bank.dtos.responses;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal principalBalance,
        BigDecimal loans,
        BigDecimal loanInterests

) { }
