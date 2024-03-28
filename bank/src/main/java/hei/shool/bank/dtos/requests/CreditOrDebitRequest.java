package hei.shool.bank.dtos.requests;


public record CreditOrDebitRequest (
  String accountNumber,

  String password,

  Double amount,

  String description,

  Long categoryId
){ }
