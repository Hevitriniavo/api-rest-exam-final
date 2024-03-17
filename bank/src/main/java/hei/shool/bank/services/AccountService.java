package hei.shool.bank.services;

import hei.shool.bank.entites.Account;

import java.util.List;

public interface AccountService {
    Account saveOrUpdate(Account account);

    List<Account> findAll();

    Account deleteById(Long id);

    Account findById(Long id);

    List<Account> findByLastName(String value);
}
