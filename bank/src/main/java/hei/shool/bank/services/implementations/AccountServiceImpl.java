package hei.shool.bank.services.implementations;

import hei.shool.bank.entites.Account;
import hei.shool.bank.repositories.GenericRepository;
import hei.shool.bank.services.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final GenericRepository<Account, Long> repository;

    public AccountServiceImpl(GenericRepository<Account, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Account saveOrUpdate(Account account) {
        return this.repository.saveOrUpdate(account);
    }
}
