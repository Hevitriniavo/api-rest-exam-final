package hei.shool.bank.repositories;

import hei.shool.bank.entities.LoanHistory;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class LoanHistoryRepository extends AbstractCrudOperations<LoanHistory, Long> {
    public LoanHistoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
