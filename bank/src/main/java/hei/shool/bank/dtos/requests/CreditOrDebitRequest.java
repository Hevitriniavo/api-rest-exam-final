package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;

public record CreditOrDebitRequest (
  String accountNumber,
  String password,

  BigDecimal amount,

  String description
){ }
