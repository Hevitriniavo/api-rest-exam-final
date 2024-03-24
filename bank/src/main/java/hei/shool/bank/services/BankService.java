package hei.shool.bank.services;

import hei.shool.bank.entities.Bank;
import hei.shool.bank.helpers.Paginate;

import java.util.List;

public interface BankService {
    List<Bank> findAll();

    Bank createOrUpdate(Bank bank);



    Paginate<Bank> paginate(Long pageNumber, Long pageSize);

    Bank deleteById(Long id);

    Bank findById(Long id);
}
