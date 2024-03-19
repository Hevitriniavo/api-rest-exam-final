package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.entites.Account;
import hei.shool.bank.entites.Transaction;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImplement implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransactionServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Map<String, String> creditMoney(CreditOrDebitRequest creditOrDebitRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(creditOrDebitRequest.accountId());
        if (optionalAccount.isEmpty()) {
            return Map.of("message", "No account found with id " + creditOrDebitRequest.accountId());
        }
        Account account = optionalAccount.get();
        if (account.credit(creditOrDebitRequest.amount())) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(creditOrDebitRequest.accountId());
            transaction.setAmount(creditOrDebitRequest.amount());
            transaction.setReason("Credit of money");
            transaction.setEffectiveDate(LocalDate.now());
            transaction.setRecordDate(LocalDateTime.now());
            transaction.setTransactionType("CREDIT");
            transactionRepository.saveOrUpdate(transaction);
            accountRepository.saveOrUpdate(account);
            return Map.of("message", "Credit completed successfully.");
        } else {
            return Map.of("message", "Insufficient balance to make this credit.");
        }
    }

    @Override
    public Map<String, String> debitMoney(CreditOrDebitRequest creditOrDebitRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(creditOrDebitRequest.accountId());
        if (optionalAccount.isEmpty()) {
            return Map.of("message", "No account found with id " + creditOrDebitRequest.accountId());
        }
        Account account = optionalAccount.get();
        if (account.debit(creditOrDebitRequest.amount())) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(creditOrDebitRequest.accountId());
            transaction.setAmount(creditOrDebitRequest.amount());
            transaction.setReason("Debit of money");
            transaction.setEffectiveDate(LocalDate.now());
            transaction.setRecordDate(LocalDateTime.now());
            transaction.setTransactionType("DEBIT");
            transactionRepository.saveOrUpdate(transaction);
            accountRepository.saveOrUpdate(account);
            return Map.of("message", "Debit completed successfully.");
        } else {
            return Map.of("message", "Insufficient balance to make this debit.");
        }
    }
}
