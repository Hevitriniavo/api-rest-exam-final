package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.entites.Account;
import hei.shool.bank.mappers.AccountMapper;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImplement(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }


    @Override
    public AccountResponse saveOrUpdate(AccountRequest account) {
        Account savedAccount = accountRepository.saveOrUpdate(accountMapper.fromDTO(account));
        return accountMapper.fromEntity(savedAccount);
    }

    @Override
    public List<AccountResponse> findAll() {
        return accountRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparingLong(Account::getId))
                .map(accountMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponse deleteById(Long id) {
        Account deletedAccount = accountRepository.deleteById(id);
        if (deletedAccount != null) {
            return accountMapper.fromEntity(deletedAccount);
        } else {
            return null;
        }
    }

    @Override
    public AccountResponse findById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.map(accountMapper::fromEntity).orElse(null);
    }
}
