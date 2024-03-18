package hei.shool.bank.repositories;

import hei.shool.bank.entites.TransactionCategory;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class TransactionCategoryRepository extends AbstractCrudOperations<TransactionCategory, Long> {
    public TransactionCategoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
