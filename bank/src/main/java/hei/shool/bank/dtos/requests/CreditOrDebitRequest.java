package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;

public record CreditOrDebitRequest(
         Long accountId,
         BigDecimal amount,
         String reason
) {}
