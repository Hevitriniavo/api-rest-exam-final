package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.BankRequest;
import hei.shool.bank.dtos.responses.BankResponse;
import hei.shool.bank.entites.Bank;
import hei.shool.bank.mappers.BankMapper;
import hei.shool.bank.repositories.BankRepository;
import hei.shool.bank.services.BankService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankServiceImplement implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    public BankServiceImplement(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }


    @Override
    public BankResponse saveOrUpdate(BankRequest bankRequest) {
        Bank bank = bankMapper.fromDTO(bankRequest);
        Bank savedBank = bankRepository.saveOrUpdate(bank);
        return bankMapper.fromEntity(savedBank);
    }

    @Override
    public List<BankResponse> findAll() {
        return bankRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparingLong(Bank::getId))
                .map(bankMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BankResponse deleteById(Long id) {
        Bank deletedBank = bankRepository.deleteById(id);
        if (deletedBank != null) {
            return bankMapper.fromEntity(deletedBank);
        } else {
            return null;
        }
    }

    @Override
    public BankResponse findById(Long id) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        return optionalBank.map(bankMapper::fromEntity).orElse(null);
    }
}
