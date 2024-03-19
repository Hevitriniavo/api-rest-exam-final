package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.CreditRequest;
import hei.shool.bank.services.impletentations.CreditServiceImplement;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/credit")
public class CreditController {

    private final CreditServiceImplement withdrawalService;

    public CreditController(CreditServiceImplement withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping
    public Map<String, String> creditMoney(@RequestBody CreditRequest creditRequest) {
        return withdrawalService.withdrawMoney(creditRequest);
    }
}
