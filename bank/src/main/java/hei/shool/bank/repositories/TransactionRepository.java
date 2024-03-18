package hei.shool.bank.repositories;

import hei.shool.bank.entites.Transaction;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class TransactionRepository extends AbstractCrudOperations<Transaction, Long> {
    public TransactionRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
