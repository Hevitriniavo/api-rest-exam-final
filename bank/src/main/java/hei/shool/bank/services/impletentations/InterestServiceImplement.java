package hei.shool.bank.services.impletentations;

import hei.shool.bank.entites.Account;
import hei.shool.bank.entites.Interest;
import hei.shool.bank.entites.Transaction;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.InterestRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.InterestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class InterestServiceImplement implements InterestService {

    private final InterestRepository interestRepository;
    private final TransactionRepository transactionRepository;

    public InterestServiceImplement(InterestRepository interestRepository, TransactionRepository transactionRepository) {
        this.interestRepository = interestRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void applyInterest(Account account) {
        if (account.getBalance() < 0) {
            List<Transaction> transactions = transactionRepository.findAll();
            LocalDate lastPaymentDate = transactions.stream()
                    .filter(transaction -> transaction.getAccountId().equals(account.getId()) && TransactionType.CREDIT.name().equals(transaction.getTransactionType().name()))
                    .max(Comparator.comparing(Transaction::getEffectiveDate))
                    .map(Transaction::getEffectiveDate)
                    .orElse(account.getCreationDate());

            LocalDate currentDate = LocalDate.now();
            long daysOverdue = ChronoUnit.DAYS.between(lastPaymentDate, currentDate);
            double interestRate;
            if (daysOverdue < 7) {
                interestRate = 0.01;
            } else {
                interestRate = 0.02;
            }
            double interestAmount = account.getBalance() * interestRate;
            Interest interest = new Interest();
            interest.setAccountId(account.getId());
            interest.setAmount(interestAmount);
            interest.setInterestRate(interestRate);
            interest.setInterestDate(LocalDate.from(currentDate));
            interestRepository.saveOrUpdate(interest);
            account.setBalance(account.getBalance() + interestAmount);
        }
    }
}
