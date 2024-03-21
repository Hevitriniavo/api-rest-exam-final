package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.CreateAccountRequest;
import hei.shool.bank.dtos.requests.UpdateAccountRequest;
import hei.shool.bank.dtos.requests.OperationResult;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.entites.Account;
import hei.shool.bank.mappers.AccountMapper;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.services.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    public AccountResponse create(CreateAccountRequest accountRequest)  {
        Account account = accountMapper.fromDTO(accountRequest);
        if (account.getId() == null){
            account.setCreationDate(LocalDate.now());
        }
        account.setOverdraftLimit(account.getNetMonthlySalary().divide(BigDecimal.valueOf(3.00), 2, RoundingMode.HALF_UP));
        Account savedAccount = accountRepository.saveOrUpdate(account);
        return accountMapper.fromEntity(savedAccount);
    }

    @Override
    public AccountResponse update(UpdateAccountRequest updateAccountRequest) {
        Account savedAccount = accountRepository.findById(updateAccountRequest.id()).orElse(null);
        if (savedAccount == null) {
            return null;
        }
        savedAccount.setEmail(updateAccountRequest.email());
        savedAccount.setPassword(updateAccountRequest.password());
        savedAccount.setLastName(updateAccountRequest.lastName());
        savedAccount.setFirstName(updateAccountRequest.firstName());
        savedAccount.setBirthday(updateAccountRequest.birthday());
        savedAccount.setOverdraftLimit(updateAccountRequest.netMonthlySalary().divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
        savedAccount.setNetMonthlySalary(updateAccountRequest.netMonthlySalary());
        Account updatedAccount = accountRepository.saveOrUpdate(savedAccount);
        return accountMapper.fromEntity(updatedAccount);
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

    @Override
    public OperationResult toggleOverdraft(Long accountId, boolean enable) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return new OperationResult(false, "Compte introuvable avec identifiant : " + accountId);
        }
        Account account = optionalAccount.get();
        account.setOverdraftEnabled(enable);
        accountRepository.saveOrUpdate(account);
        return new OperationResult(true, "Statut de découvert mis à jour avec succès.");
    }

}
