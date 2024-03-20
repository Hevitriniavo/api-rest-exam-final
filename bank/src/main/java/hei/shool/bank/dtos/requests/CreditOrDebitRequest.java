package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;

public record CreditOrDebitRequest (
        BigDecimal amount,
        Long accountId,
        String reason
){}
