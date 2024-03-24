package hei.shool.bank.dtos.responses;

public record CreditOrDebitResponse (
  boolean isSuccess,
  String message
){}
