package hei.shool.bank.repositories;

import hei.shool.bank.entities.BankSold;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class BankSoldRepository extends AbstractCrudOperations<BankSold, Long> {
    public BankSoldRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
