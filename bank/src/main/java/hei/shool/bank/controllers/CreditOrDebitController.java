package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.CreditOrDebitResponse;
import hei.shool.bank.services.CreditOrDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreditOrDebitController {

    private final CreditOrDebitService creditOrDebitService;

    @PostMapping("/credit")
    public ResponseEntity<CreditOrDebitResponse> credit(@RequestBody CreditOrDebitRequest request) {
        CreditOrDebitResponse response = creditOrDebitService.credit(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<CreditOrDebitResponse> debit(@RequestBody CreditOrDebitRequest request) {
        CreditOrDebitResponse response = creditOrDebitService.debit(request);
        return ResponseEntity.ok(response);
    }
}
