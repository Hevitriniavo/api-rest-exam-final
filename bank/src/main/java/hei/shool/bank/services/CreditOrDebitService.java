package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.OperationResponse;

public interface CreditOrDebitService {

    OperationResponse credit (CreditOrDebitRequest request);
    OperationResponse debit (CreditOrDebitRequest request);

    OperationResponse activeOverDraft(String accountNumber);
}
