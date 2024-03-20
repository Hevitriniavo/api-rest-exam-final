package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.AccountRequest;
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

    @PostMapping
    public AccountResponse createOrUpdateAccount(@RequestBody AccountRequest account){
        return accountService.saveOrUpdate(account);
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
