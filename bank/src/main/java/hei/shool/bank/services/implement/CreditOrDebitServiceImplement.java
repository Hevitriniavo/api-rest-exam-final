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
        Account account = accountRepository.findByField("accountNumber", request.accountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));

        if (!account.getPassword().equals(request.password())) {
            return new CreditOrDebitResponse(false, "Mot de passe incorrect");
        }

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(request.amount());
        account.setBalance(newBalance);
        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .amount(request.amount())
                .reason(request.description() != null ? request.description() : "Credit")
                .transactionType(TransactionType.CREDIT)
                .comment(request.description())
                .build();
        Transaction savedTransaction = transactionRepository.saveOrUpdate(transaction);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .transactionId(savedTransaction.getId())
                .operationDate(LocalDate.now())
                .build();
        transactionHistoryRepository.saveOrUpdate(transactionHistory);
        accountRepository.saveOrUpdate(account);

        return new CreditOrDebitResponse(true, "Opération de crédit réussie");
    }

    @Override
    public CreditOrDebitResponse debit(CreditOrDebitRequest request) {
        Account account = accountRepository.findByField("accountNumber", request.accountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));

        if (!account.getPassword().equals(request.password())) {
            return new CreditOrDebitResponse(false, "Mot de passe incorrect.");
        }

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(request.amount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return new CreditOrDebitResponse(false, "Fonds insuffisants");
        }

        account.setBalance(newBalance);
        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .amount(request.amount())
                .reason(request.description() != null ? request.description(): "Debit")
                .transactionType(TransactionType.DEBIT)
                .comment(request.description())
                .build();
        Transaction savedTransaction = transactionRepository.saveOrUpdate(transaction);
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .transactionId(savedTransaction.getId())
                .operationDate(LocalDate.now())
                .build();
        transactionHistoryRepository.saveOrUpdate(transactionHistory);
        accountRepository.saveOrUpdate(account);
        return new CreditOrDebitResponse(true, "Opération de débit réussie");
    }

    @Override
    public boolean activeOverDraft(String  accountNumber) {
        Account account = accountRepository.findByField("accountNumber", accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));
        account.setOverdraftEnabled(true);
        Account savedAccount = accountRepository.saveOrUpdate(account);
        return savedAccount.isOverdraftEnabled();
    }

}
