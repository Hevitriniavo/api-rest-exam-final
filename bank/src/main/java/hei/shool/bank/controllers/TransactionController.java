package hei.shool.bank.controllers;

import hei.shool.bank.dtos.responses.TransactionResponse;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionResponse> transactions = transactionService.findTransactionByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}/paginate")
    public ResponseEntity<Paginate<TransactionResponse>> getPaginatedTransactionsByAccountId(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "1") Long pageNumber,
            @RequestParam(defaultValue = "10") Long pageSize
    ) {
        Paginate<TransactionResponse> paginatedTransactions = transactionService.paginateTransactionsByAccountId(accountId, pageNumber, pageSize);
        return new ResponseEntity<>(paginatedTransactions, HttpStatus.OK);
    }
}
