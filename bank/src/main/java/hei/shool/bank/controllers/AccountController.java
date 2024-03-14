package hei.shool.bank.controllers;

import hei.shool.bank.entites.Account;
import hei.shool.bank.services.AccountService;
import org.springframework.web.bind.annotation.*;

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
}
