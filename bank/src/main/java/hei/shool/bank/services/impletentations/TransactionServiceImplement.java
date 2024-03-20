package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.CreditOrDebitResponse;
import hei.shool.bank.entites.Account;
import hei.shool.bank.entites.Interest;
import hei.shool.bank.entites.Transaction;
import hei.shool.bank.entites.TransactionsHistory;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.InterestRepository;
import hei.shool.bank.repositories.TransactionHistoryRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TransactionServiceImplement implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final InterestRepository interestRepository;
    public TransactionServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionHistoryRepository transactionHistoryRepository, InterestRepository interestRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.interestRepository = interestRepository;
    }

    @Override
    public CreditOrDebitResponse credit(CreditOrDebitRequest request) {
        return processTransaction(request, TransactionType.CREDIT);
    }
    @Override
    public CreditOrDebitResponse debit(CreditOrDebitRequest request) {
        return processTransaction(request, TransactionType.DEBIT);
    }

    private CreditOrDebitResponse processTransaction(CreditOrDebitRequest request, TransactionType transactionType) {
        Account account = findAccount(request.accountId());
        if (account == null) {
            return new CreditOrDebitResponse(false, "Compte non trouvé.");
        }

        if (transactionType == TransactionType.CREDIT) {
            processCredit(account, request.amount());
        } else if (transactionType == TransactionType.DEBIT) {
            CreditOrDebitResponse response = processDebit(account, request.amount());
            if (!response.isSuccess()) {
                return response;
            }
        }

        recordTransaction(account, request, transactionType);
        return new CreditOrDebitResponse(true, transactionType == TransactionType.CREDIT ? "Crédit réussi." : "Débit réussi.");
    }

    private Account findAccount(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.orElse(null);
    }

    private void processCredit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        if (account.getBalance().compareTo(BigDecimal.ZERO) >= 0 && account.isOverdraftEnabled()) {
            account.setOverdraftEnabled(false);
        }
        accountRepository.saveOrUpdate(account);
    }

    private CreditOrDebitResponse processDebit(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0 && !account.isOverdraftEnabled()) {
            return new CreditOrDebitResponse(false, "Solde insuffisant.");
        }
        account.setBalance(account.getBalance().subtract(amount));
        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal overdraftAmount = account.getBalance().abs();
            calculateAndStoreInterest(account, overdraftAmount);
            account.setLastWithdrawalDate(LocalDate.now());
        }
        accountRepository.saveOrUpdate(account);
        return new CreditOrDebitResponse(true, "Débit réussi.");
    }

    private void recordTransaction(Account account, CreditOrDebitRequest request, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
                .effectiveDate(LocalDate.now())
                .amount(request.amount())
                .reason(request.reason())
                .transactionType(transactionType)
                .accountId(account.getId())
                .build();
        Transaction savedTransaction = transactionRepository.saveOrUpdate(transaction);
        TransactionsHistory history = TransactionsHistory.builder()
                .transactionId(savedTransaction.getId())
                .operationDate(LocalDateTime.now())
                .build();
        transactionHistoryRepository.saveOrUpdate(history);
    }


    private void calculateAndStoreInterest(Account account, BigDecimal overdraftAmount) {
        if (overdraftAmount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal interestAmount = calculateInterest(account, overdraftAmount);
            account.setBalance(account.getBalance().subtract(interestAmount));
            storeInterest(account, account.getBalance());
        }
    }

    private BigDecimal calculateInterest(Account account, BigDecimal overdraftAmount) {
        LocalDate today = LocalDate.now();
        LocalDate lastWithdrawalDate = account.getLastWithdrawalDate();
        if (lastWithdrawalDate == null) {
            lastWithdrawalDate = account.getCreationDate();
        }
        long daysOverdrawn = ChronoUnit.DAYS.between(lastWithdrawalDate, today);
        BigDecimal interestRate = daysOverdrawn < 7 ? BigDecimal.valueOf(0.01) : BigDecimal.valueOf(0.02);
        return overdraftAmount.multiply(interestRate).multiply(BigDecimal.valueOf(daysOverdrawn));
    }

    private void storeInterest(Account account, BigDecimal interestAmount) {
        Interest interest = Interest.builder()
                .accountId(account.getId())
                .amount(interestAmount)
                .interestDate(LocalDate.now())
                .interestRate(interestAmount.abs())
                .build();
        interestRepository.saveOrUpdate(interest);
    }

}
