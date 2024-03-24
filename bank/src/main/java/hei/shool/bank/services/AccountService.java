package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.requests.LoginRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.dtos.responses.PaginateAccountResponse;

import java.util.List;

public interface AccountService {
    List<AccountResponse> findAll();

    AccountResponse createOrUpdate(AccountRequest accountRequest);

    PaginateAccountResponse paginate(Long pageNumber, Long pageSize);

    AccountResponse deleteById(Long id);

    AccountResponse findById(Long id);
    AccountResponse login(LoginRequest loginRequest);
}
