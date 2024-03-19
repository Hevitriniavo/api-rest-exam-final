package hei.shool.bank.dtos.requests;

public record CreditOrDebitRequest(
         Long accountId,
         Double amount,
         String reason
) {}
