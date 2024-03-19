package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;

import java.util.Map;

public interface TransactionService {

    Map<String, String> creditMoney(CreditOrDebitRequest creditOrDebitRequest);
    Map<String, String> debitMoney(CreditOrDebitRequest creditOrDebitRequest);
}
