package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
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
    public Map<String, String> creditMoney(@RequestBody CreditOrDebitRequest creditOrDebitRequest) {
        return withdrawalService.creditMoney(creditOrDebitRequest);
    }

    @RequestMapping("/debit")
    public Map<String, String> debitMoney(@RequestBody CreditOrDebitRequest creditOrDebitRequest) {
        return withdrawalService.debitMoney(creditOrDebitRequest);
    }
}
