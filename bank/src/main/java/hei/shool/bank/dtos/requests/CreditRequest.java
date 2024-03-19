package hei.shool.bank.dtos.requests;

public record CreditRequest(
         Long accountId,
         Double amount
) { }
