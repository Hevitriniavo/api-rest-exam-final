package hei.shool.bank.dtos.responses;

import hei.shool.bank.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionResponse (
        Long id,
        AccountResponse accounts,
        BigDecimal amount,
        String reason,
        TransactionType transactionType,
        Long categoryId,
        String comment
) {}