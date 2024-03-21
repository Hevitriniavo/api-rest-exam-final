package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.CreateAccountRequest;
import hei.shool.bank.dtos.requests.UpdateAccountRequest;
import hei.shool.bank.dtos.requests.OperationResult;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/accounts")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> getAccounts(){
        return accountService.findAll();
    }

    @PutMapping("/update")
    public AccountResponse updateAccount(@RequestBody UpdateAccountRequest account){
        return accountService.update(account);
    }

    @PostMapping("/create")
    public AccountResponse createAccount(@RequestBody CreateAccountRequest account){
        return accountService.create(account);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id){
        return accountService.findById(id);
    }

    @DeleteMapping("/{id}")
    public AccountResponse destroyAccount(@PathVariable Long id){
        return accountService.deleteById(id);
    }

    @PutMapping("/{accountId}/overdraft")
    public OperationResult toggleOverdraft(@PathVariable Long accountId, @RequestParam boolean enable) {
        return accountService.toggleOverdraft(accountId, enable);
    }
}
