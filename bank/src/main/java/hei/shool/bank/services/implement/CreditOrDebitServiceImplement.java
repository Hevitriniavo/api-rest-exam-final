package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.CreditOrDebitResponse;
import hei.shool.bank.entities.Account;
import hei.shool.bank.entities.Transaction;
import hei.shool.bank.entities.TransactionHistory;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransactionHistoryRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.CreditOrDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreditOrDebitServiceImplement implements CreditOrDebitService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public CreditOrDebitResponse credit(CreditOrDebitRequest request) {
        Account account = findAccountByNumber(request.accountNumber(), request.password());
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(request.amount());
        account.setBalance(newBalance);
        Transaction transaction = buildTransaction(account.getId(), request.amount(), request.description(), TransactionType.CREDIT);
        Transaction savedTransaction = transactionRepository.saveOrUpdate(transaction);
        TransactionHistory transactionHistory = buildTransactionHistory(savedTransaction.getId());
        transactionHistoryRepository.saveOrUpdate(transactionHistory);
        accountRepository.saveOrUpdate(account);
        return new CreditOrDebitResponse(true, "Opération de crédit réussie");
    }

    @Override
    public CreditOrDebitResponse debit(CreditOrDebitRequest request) {
        Account account = findAccountByNumber(request.accountNumber(), request.password());
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(request.amount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return new CreditOrDebitResponse(false, "Fonds insuffisants");
        }
        account.setBalance(newBalance);
        Transaction transaction = buildTransaction(account.getId(), request.amount(), request.description(), TransactionType.DEBIT);
        Transaction savedTransaction = transactionRepository.saveOrUpdate(transaction);
        TransactionHistory transactionHistory = buildTransactionHistory(savedTransaction.getId());
        transactionHistoryRepository.saveOrUpdate(transactionHistory);
        accountRepository.saveOrUpdate(account);
        return new CreditOrDebitResponse(true, "Opération de débit réussie");
    }
    @Override
    public boolean activeOverDraft(String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        account.setOverdraftEnabled(true);
        Account savedAccount = accountRepository.saveOrUpdate(account);
        return savedAccount.isOverdraftEnabled();
    }

    private Account findAccountByNumber(String accountNumber) {
        return accountRepository.findByField("accountNumber", accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));
    }

    private Account findAccountByNumber(String accountNumber, String password) {
        Account account = findAccountByNumber(accountNumber);
        verifyPassword(account, password);
        return account;
    }

    private void verifyPassword(Account account, String password) {
        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }
    }

    private Transaction buildTransaction(Long accountId, BigDecimal amount, String description, TransactionType transactionType) {
        return Transaction.builder()
                .accountId(accountId)
                .amount(amount)
                .reason(description != null ? description : "")
                .transactionType(transactionType)
                .comment(description)
                .build();
    }

    private TransactionHistory buildTransactionHistory(Long transactionId) {
        return TransactionHistory.builder()
                .transactionId(transactionId)
                .operationDate(LocalDate.now())
                .build();
    }
}
