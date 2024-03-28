package hei.shool.bank.repositories;

import hei.shool.bank.entities.Loan;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class LoanRepository extends AbstractCrudOperations<Loan, Long> {
    public LoanRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
