package hei.shool.bank.services;


import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.responses.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse saveOrUpdate(AccountRequest account);

    List<AccountResponse> findAll();

    AccountResponse deleteById(Long id);

    AccountResponse findById(Long id);

}
