package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.responses.OperationResponse;
import hei.shool.bank.entities.Account;
import hei.shool.bank.entities.Category;
import hei.shool.bank.entities.Transaction;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.CategoryRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.CreditOrDebitService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class CreditOrDebitServiceImplement implements CreditOrDebitService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;

    public CreditOrDebitServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public OperationResponse credit(CreditOrDebitRequest request) {
        return processTransaction(request, TransactionType.CREDIT, "Opération de crédit réussie");
    }

    @Override
    public OperationResponse debit(CreditOrDebitRequest request) {
        return processTransaction(request, TransactionType.DEBIT, "Opération de débit réussie");
    }

    private Optional<Account> findAccountAndVerifyPassword(CreditOrDebitRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByField("accountNumber", request.accountNumber());
        if (optionalAccount.isEmpty()) {
            return Optional.empty();
        }

        Account account = optionalAccount.get();
        if (!account.getPassword().equals(request.password())) {
            return Optional.empty();
        }

        return Optional.of(account);
    }

    private OperationResponse performTransaction(Account account, CreditOrDebitRequest request, TransactionType transactionType, String successMessage) {
        Optional<Category> optionalCategory = categoryRepository.findById(request.categoryId());
        if (optionalCategory.isEmpty()) {
            return new OperationResponse(false, "Catégorie non trouvée. Veuillez fournir une catégorie valide.");
        }

        Category category = optionalCategory.get();
        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .categoryId(category.getId())
                .type(transactionType)
                .description(request.description())
                .operationDate(LocalDateTime.now())
                .amount(request.amount())
                .build();

        if (transactionType == TransactionType.CREDIT) {
            account.credit(request.amount());
        } else {
            if (account.isOverdraftEnabled() && (account.getBalance() + account.getOverdraftLimit() >=  request.amount())) {
                account.debit(request.amount());
            } else if (!account.isOverdraftEnabled() && account.getBalance() >= request.amount()) {
                account.debit(request.amount());
            } else {
                return new OperationResponse(false, "Solde insuffisant pour effectuer cette opération");
            }
        }

        transactionRepository.saveOrUpdate(transaction);
        accountRepository.saveOrUpdate(account);
        return new OperationResponse(true, successMessage);
    }

    public OperationResponse processTransaction(CreditOrDebitRequest request, TransactionType transactionType, String successMessage) {
        Optional<Account> optionalAccount = findAccountAndVerifyPassword(request);
        return optionalAccount.map(account -> performTransaction(account, request, transactionType, successMessage))
                .orElseGet(() -> new OperationResponse(false, "Compte non trouvé. Une erreur s'est produite lors de l'opération"));

    }

    @Override
    public OperationResponse activeOverDraft(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findByField("accountNumber", accountNumber);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setOverdraftEnabled(true);
            accountRepository.saveOrUpdate(account);
            return new OperationResponse(true, "Le découvert est activé pour le compte.");
        } else {
            return new OperationResponse(false, "Compte non trouvé.");
        }
    }
}
