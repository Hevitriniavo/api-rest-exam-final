package hei.shool.bank.dtos.responses;

public record TransactionResponse (
        Long id,
        AccountResponse accounts,
        Double amount,
        Long categoryId,
        String comment
) {}