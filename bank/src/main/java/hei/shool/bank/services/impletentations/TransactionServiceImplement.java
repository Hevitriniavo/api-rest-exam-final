package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.services.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Override
    public boolean creditMoney(CreditOrDebitRequest creditOrDebitRequest) {
        return false;
    }

    @Override
    public boolean debitMoney(CreditOrDebitRequest creditOrDebitRequest) {
        return false;
    }

    @Override
    public boolean transferEnterTwoAccount(TransferRequest transferRequest) {
        return false;
    }
}
