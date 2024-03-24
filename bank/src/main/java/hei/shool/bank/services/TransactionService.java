package hei.shool.bank.services;

import hei.shool.bank.dtos.responses.TransactionResponse;
import hei.shool.bank.helpers.Paginate;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> findAll();
    List<TransactionResponse>  findTransactionByAccountId(Long accountId);

    Paginate<TransactionResponse>  paginateTransactionsByAccountId(Long accountId, Long pageNumber, Long pageSize);
}
