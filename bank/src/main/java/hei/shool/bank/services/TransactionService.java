package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.requests.TransferRequest;


public interface TransactionService {

    boolean creditMoney(CreditOrDebitRequest creditOrDebitRequest);
    boolean debitMoney(CreditOrDebitRequest creditOrDebitRequest);

    boolean transferEnterTwoAccount(TransferRequest transferRequest);
}
