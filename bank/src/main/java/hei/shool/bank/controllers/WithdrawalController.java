package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.WithdrawalRequest;
import hei.shool.bank.services.impletentations.WithdrawalServiceImplement;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/withdrawal")
public class WithdrawalController {

    private final WithdrawalServiceImplement withdrawalService;

    public WithdrawalController(WithdrawalServiceImplement withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping
    public Map<String, String> withdrawMoney(@RequestBody WithdrawalRequest withdrawalRequest) {
        return withdrawalService.withdrawMoney(withdrawalRequest);
    }
}
