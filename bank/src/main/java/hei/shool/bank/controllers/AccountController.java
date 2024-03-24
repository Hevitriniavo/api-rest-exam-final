package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.requests.LoginRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.dtos.responses.PaginateAccountResponse;
import hei.shool.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<AccountResponse> login(@RequestBody LoginRequest loginRequest) {
        AccountResponse accountResponse = accountService.login(loginRequest);
        if (accountResponse != null) {
            return ResponseEntity.ok(accountResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse accountResponse = accountService.findById(id);
        if (accountResponse != null) {
            return ResponseEntity.ok(accountResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts() {
        List<AccountResponse> accountResponse = accountService.findAll();
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        AccountResponse createdAccount = accountService.createOrUpdate(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountRequest accountRequest) {
        AccountResponse updatedAccount = accountService.createOrUpdate(accountRequest);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponse> deleteAccount(@PathVariable Long id) {
        AccountResponse deletedAccount = accountService.deleteById(id);
        return ResponseEntity.ok(deletedAccount);
    }

    @GetMapping("/paginate")
    public ResponseEntity<PaginateAccountResponse> getAccountsPaginated(
            @RequestParam(defaultValue = "1") Long pageNumber,
            @RequestParam(defaultValue = "10") Long pageSize
    ) {
        PaginateAccountResponse paginateResult = accountService.paginate(pageNumber, pageSize);
        return ResponseEntity.ok(paginateResult);
    }
}
