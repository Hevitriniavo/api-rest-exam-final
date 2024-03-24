package hei.shool.bank.controllers;

import hei.shool.bank.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {
    private BankService bankService;

}
