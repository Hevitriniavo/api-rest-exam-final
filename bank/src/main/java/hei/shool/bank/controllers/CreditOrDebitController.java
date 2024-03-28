package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.OperationResponse;
import hei.shool.bank.services.implement.CreditOrDebitServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CreditOrDebitController {

    private final CreditOrDebitServiceImplement creditOrDebitService;

    @PostMapping("/credit")
    public ResponseEntity<OperationResponse> credit(@RequestBody CreditOrDebitRequest request) {
        OperationResponse response = creditOrDebitService.credit(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/debit")
    public ResponseEntity<OperationResponse> debit(@RequestBody CreditOrDebitRequest request) {
        OperationResponse response = creditOrDebitService.debit(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/activate-overdraft/{accountNumber}")
    public ResponseEntity<OperationResponse> activeOverDraft(@PathVariable String accountNumber) {
        OperationResponse result = creditOrDebitService.activeOverDraft(accountNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
