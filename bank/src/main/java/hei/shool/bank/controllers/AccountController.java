package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> getAccounts(){
        return this.accountService.findAll();
    }

    @PostMapping("/create")
    public AccountResponse createAccount(@RequestBody AccountRequest account){
        return this.accountService.saveOrUpdate(account);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id){
        return this.accountService.findById(id);
    }

    @DeleteMapping("/{id}")
    public AccountResponse destroyAccount(@PathVariable Long id){
        return this.accountService.deleteById(id);
    }
}
