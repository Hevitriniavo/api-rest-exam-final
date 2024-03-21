package hei.shool.bank.controllers;


import hei.shool.bank.dtos.responses.BalanceResponse;
import hei.shool.bank.services.BalanceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/{accountId}")
    public BalanceResponse getConsultBalance(@PathVariable Long accountId, @RequestParam(required = false) LocalDate selectedDate){
        return balanceService.getConsultBalance(accountId, Objects.requireNonNullElseGet(selectedDate, LocalDate::now));
    }
}
