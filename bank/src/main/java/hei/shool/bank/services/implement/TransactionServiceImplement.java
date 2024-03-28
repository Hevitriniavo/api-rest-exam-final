package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.dtos.responses.TransactionResponse;
import hei.shool.bank.entities.Transaction;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.AccountService;
import hei.shool.bank.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    public TransactionServiceImplement(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionResponse> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<TransactionResponse> findTransactionByAccountId(Long accountId) {
        return transactionRepository.findTransactionByAccountId(accountId);
    }

    @Override
    public Paginate<TransactionResponse> paginateTransactionsByAccountId(Long accountId, Long pageNumber, Long pageSize) {
        return transactionRepository.paginateTransactionsByAccountId( accountId,  pageNumber,  pageSize);
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        AccountResponse accountResponse = accountService.findById(transaction.getAccountId());
        return new TransactionResponse(
                transaction.getId(),
                accountResponse,
                transaction.getAmount(),
                transaction.getCategoryId(),
                transaction.getDescription()
        );
    }
}
