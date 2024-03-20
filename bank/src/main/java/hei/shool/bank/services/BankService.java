package hei.shool.bank.services;


import hei.shool.bank.dtos.requests.BankRequest;
import hei.shool.bank.dtos.responses.BankResponse;

import java.util.List;

public interface BankService {
    BankResponse saveOrUpdate(BankRequest bankRequest);

    List<BankResponse> findAll();

    BankResponse deleteById(Long id);

    BankResponse findById(Long id);

}
