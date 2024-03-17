package hei.shool.bank.controllers;

import hei.shool.bank.entites.Account;
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

    @PostMapping
    public Account createAccount(@RequestBody Account account){
        return this.accountService.saveOrUpdate(account);
    }

    @GetMapping
    public List<Account> getAccounts(){
        return this.accountService.findAll();
    }

    @GetMapping("/lastname/{lastname}")
    public List<Account> getAccountsByLastName(@PathVariable String lastname){
        return this.accountService.findByLastName(lastname);
    }
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        return this.accountService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Account destroyAccount(@PathVariable Long id){
        return this.accountService.deleteById(id);
    }
}
