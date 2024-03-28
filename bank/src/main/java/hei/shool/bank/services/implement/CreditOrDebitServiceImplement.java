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

    private OperationResponse processTransaction(CreditOrDebitRequest request, TransactionType transactionType, String successMessage) {
        Optional<Account> optionalAccount = accountRepository.findByField("accountNumber", request.accountNumber());
        Optional<Category> optionalCategory = categoryRepository.findById(request.categoryId());

        if (optionalAccount.isPresent() && optionalCategory.isPresent()) {
            Account account = optionalAccount.get();
            if (!account.getPassword().equals(request.password())) {
                return new OperationResponse(false, "Mot de passe incorrect");
            }
            Transaction transaction = Transaction.builder()
                    .accountId(account.getId())
                    .categoryId(request.categoryId())
                    .type(transactionType)
                    .description(request.description())
                    .operationDate(LocalDateTime.now())
                    .amount(request.amount())
                    .build();
            if (transactionType == TransactionType.CREDIT) {
                account.credit(request.amount());
            } else {
                account.debit(request.amount());
            }
            transactionRepository.saveOrUpdate(transaction);
            accountRepository.saveOrUpdate(account);
            return new OperationResponse(true, successMessage);
        } else {
            if (optionalAccount.isEmpty()) {
                return new OperationResponse(false, "Compte non trouvé. Une erreur s'est produite lors de l'opération");
            } else {
                return new OperationResponse(false, "Catégorie non trouvée. Veuillez fournir une catégorie valide.");
            }
        }
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
