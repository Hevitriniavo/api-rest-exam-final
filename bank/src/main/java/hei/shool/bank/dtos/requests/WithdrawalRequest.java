package hei.shool.bank.dtos.requests;

public record WithdrawalRequest(
         Long accountId,
         Double amount
) { }
