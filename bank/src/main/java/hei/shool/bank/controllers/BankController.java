package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.BankRequest;
import hei.shool.bank.dtos.responses.BankResponse;
import hei.shool.bank.services.BankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/banks")
public class BankController {


    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @GetMapping
    public List<BankResponse> getBanks(){
        return this.bankService.findAll();
    }

    @PostMapping
    public BankResponse createOrUpdateBank(@RequestBody BankRequest bankRequest){
        return this.bankService.saveOrUpdate(bankRequest);
    }

    @GetMapping("/{id}")
    public BankResponse getBank(@PathVariable Long id){
        return this.bankService.findById(id);
    }

    @DeleteMapping("/{id}")
    public BankResponse destroyBank(@PathVariable Long id){
        return this.bankService.deleteById(id);
    }
}
