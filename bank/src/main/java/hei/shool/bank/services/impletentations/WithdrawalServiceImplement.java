package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.WithdrawalRequest;
import hei.shool.bank.entites.Account;
import hei.shool.bank.entites.Transaction;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.WithdrawalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class WithdrawalServiceImplement implements WithdrawalService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public WithdrawalServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Map<String, String> withdrawMoney(WithdrawalRequest withdrawalRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(withdrawalRequest.accountId());
        if (optionalAccount.isEmpty()) {
            return Map.of("message", "No account found with id " + withdrawalRequest.accountId());
        }
        Account account = optionalAccount.get();
        if (account.debit(withdrawalRequest.amount())) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(withdrawalRequest.accountId());
            transaction.setAmount(withdrawalRequest.amount());
            transaction.setReason("Withdrawal of money");
            transaction.setEffectiveDate(LocalDate.now());
            transaction.setRecordDate(LocalDateTime.now());
            transaction.setTransactionType("CREDIT");
            transactionRepository.saveOrUpdate(transaction);
            account.setLastTransactionDate(LocalDateTime.now());
            accountRepository.saveOrUpdate(account);
            return Map.of("message", "Withdrawal completed successfully.");
        } else {
            return Map.of("message", "Insufficient balance to make this withdrawal.");
        }
    }
}
