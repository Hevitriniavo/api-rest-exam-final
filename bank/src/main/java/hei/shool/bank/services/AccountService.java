package hei.shool.bank.services;


import hei.shool.bank.dtos.requests.CreateAccountRequest;
import hei.shool.bank.dtos.requests.UpdateAccountRequest;
import hei.shool.bank.dtos.requests.OperationResult;
import hei.shool.bank.dtos.responses.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse update(UpdateAccountRequest account);
    AccountResponse create(CreateAccountRequest account);

    List<AccountResponse> findAll();

    AccountResponse deleteById(Long id);

    AccountResponse findById(Long id);

    OperationResult toggleOverdraft(Long accountId, boolean enable);
}
