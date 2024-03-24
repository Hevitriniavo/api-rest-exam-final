package hei.shool.bank.controllers;

import hei.shool.bank.entities.Bank;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        Bank bank = bankService.findById(id);
        if (bank != null) {
            return ResponseEntity.ok(bank);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank createdBank = bankService.createOrUpdate(bank);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBank);
    }

    @PutMapping("/update")
    public ResponseEntity<Bank> updateBank(@RequestBody Bank bank) {
        Bank updatedBank = bankService.createOrUpdate(bank);
        return ResponseEntity.ok(updatedBank);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Bank> deleteBank(@PathVariable Long id) {
        Bank deletedBank = bankService.deleteById(id);
        if (deletedBank != null) {
            return ResponseEntity.ok(deletedBank);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/paginate")
    public ResponseEntity<Paginate<Bank>> getBanksPaginated(
            @RequestParam(defaultValue = "1") Long pageNumber,
            @RequestParam(defaultValue = "10") Long pageSize
    ) {
        Paginate<Bank> paginateResult = bankService.paginate(pageNumber, pageSize);
        return ResponseEntity.ok(paginateResult);
    }

    @GetMapping
    public ResponseEntity<List<Bank>> getBanks() {
        List<Bank> bank = bankService.findAll();
        if (bank != null) {
            return ResponseEntity.ok(bank);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
