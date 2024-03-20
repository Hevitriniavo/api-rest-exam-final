package hei.shool.bank.repositories;

import hei.shool.bank.entites.Bank;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class BankRepository extends AbstractCrudOperations<Bank, Long> {
    public BankRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
