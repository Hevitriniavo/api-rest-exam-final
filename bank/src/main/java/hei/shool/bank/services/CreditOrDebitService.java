package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.CreditOrDebitResponse;

public interface CreditOrDebitService {

    CreditOrDebitResponse credit (CreditOrDebitRequest request);
    CreditOrDebitResponse debit (CreditOrDebitRequest request);
}
