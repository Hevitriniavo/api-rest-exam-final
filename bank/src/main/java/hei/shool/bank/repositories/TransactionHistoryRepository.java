package hei.shool.bank.repositories;

import hei.shool.bank.entites.TransactionsHistory;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class TransactionHistoryRepository extends AbstractCrudOperations<TransactionsHistory, Long> {
    public TransactionHistoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
