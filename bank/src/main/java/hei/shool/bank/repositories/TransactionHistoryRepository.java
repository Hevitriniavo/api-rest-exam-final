package hei.shool.bank.repositories;

import hei.shool.bank.entities.TransactionHistory;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class TransactionHistoryRepository extends AbstractCrudOperations<TransactionHistory, Long> {
    public TransactionHistoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
