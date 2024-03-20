package hei.shool.bank.controllers;


import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.CreditOrDebitResponse;
import hei.shool.bank.services.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/credit")
    public CreditOrDebitResponse credit(@RequestBody CreditOrDebitRequest request){
        return transactionService.credit(request);
    }

    @PostMapping("/debit")
    public CreditOrDebitResponse debit(@RequestBody CreditOrDebitRequest request){
        return transactionService.debit(request);
    }
}
