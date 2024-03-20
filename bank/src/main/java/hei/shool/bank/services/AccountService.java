package hei.shool.bank.services;


import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.requests.OperationResult;
import hei.shool.bank.dtos.responses.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse saveOrUpdate(AccountRequest account);

    List<AccountResponse> findAll();

    AccountResponse deleteById(Long id);

    AccountResponse findById(Long id);

    OperationResult toggleOverdraft(Long accountId, boolean enable);
}
