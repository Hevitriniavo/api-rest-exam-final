package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.requests.LoginRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.dtos.responses.PaginateAccountResponse;
import hei.shool.bank.entities.Account;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.mappers.AccountMapper;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImplement implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountResponse> findAll() {
        return  accountRepository.findAll().stream()
                .map(accountMapper::toResponse)
                .sorted(Comparator.comparing(AccountResponse::id))
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponse createOrUpdate(AccountRequest accountRequest) {
        Account account = accountMapper.toEntity(accountRequest);
        if (account.getId() == null){
            account.setCreationDate(LocalDate.now());
        }
        account = accountRepository.saveOrUpdate(account);
        return accountMapper.toResponse(account);
    }

    @Override
    public PaginateAccountResponse paginate(Long pageNumber, Long pageSize) {
        Paginate<Account> accounts = accountRepository.pagination(pageNumber, pageSize);
        List<AccountResponse> accountResponses = accounts.getItems().stream()
                .map(accountMapper::toResponse)
                .sorted(Comparator.comparing(AccountResponse::id))
                .collect(Collectors.toList());

        return new PaginateAccountResponse (
                accounts.getCount(),
                accounts.getNext(),
                accounts.getCount(),
                accountResponses
        );
    }

    @Override
    public AccountResponse deleteById(Long id) {
        Account account = accountRepository.deleteById(id);
        return accountMapper.toResponse(account);
    }

    @Override
    public AccountResponse findById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            return accountMapper.toResponse(account);
        } else {
            return null;
        }
    }

    @Override
    public AccountResponse login(LoginRequest loginRequest) {
        Optional<Account> optionalAccount = accountRepository.findByField("email", loginRequest.email());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getPassword().equals(loginRequest.password())) {
                return accountMapper.toResponse(account);
            }
        }
        return null;
    }
}
