package hei.shool.bank.services.impletentations;

import hei.shool.bank.entites.Account;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplement implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImplement(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account saveOrUpdate(Account account) {
        return this.accountRepository.saveOrUpdate(account);
    }

    @Override
    public List<Account> findAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account deleteById(Long id) {
        return this.accountRepository.deleteById(id);
    }

    @Override
    public Account findById(Long id) {
        return this.accountRepository.findById(id);
    }

    @Override
    public List<Account> findByLastName(String value) {
        return this.accountRepository.findByField("customerLastname", value);
    }
}
