package hei.shool.bank.services.implement;

import hei.shool.bank.entities.Bank;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.repositories.BankRepository;
import hei.shool.bank.services.BankService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImplement implements BankService {

    private final BankRepository bankRepository;

    public BankServiceImplement(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public Bank createOrUpdate(Bank bank) {
        return bankRepository.saveOrUpdate(bank);
    }

    @Override
    public Paginate<Bank> paginate(Long pageNumber, Long pageSize) {
        return bankRepository.pagination(pageNumber, pageSize);
    }

    @Override
    public Bank deleteById(Long id) {
        return bankRepository.deleteById(id);
    }

    @Override
    public Bank findById(Long id) {
        return bankRepository.findById(id).orElse(null);
    }
}
