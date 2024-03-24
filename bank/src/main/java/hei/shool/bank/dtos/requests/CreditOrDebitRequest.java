package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;

public record CreditOrDebitRequest (
  Long accountId,
  String password,

  BigDecimal amount,

  String description
){ }
