package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.services.impletentations.TransactionServiceImplement;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionServiceImplement withdrawalService;

    public TransactionController(TransactionServiceImplement withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @RequestMapping("/credit")
    public boolean creditMoney(@RequestBody CreditOrDebitRequest creditOrDebitRequest) {
        return withdrawalService.creditMoney(creditOrDebitRequest);
    }

    @RequestMapping("/debit")
    public boolean debitMoney(@RequestBody CreditOrDebitRequest creditOrDebitRequest) {
        return withdrawalService.debitMoney(creditOrDebitRequest);
    }

    @RequestMapping("/transfer")
    public boolean transferEnterTwoAccount(@RequestBody TransferRequest transferRequest) {
        return withdrawalService.transferEnterTwoAccount(transferRequest);
    }
}
