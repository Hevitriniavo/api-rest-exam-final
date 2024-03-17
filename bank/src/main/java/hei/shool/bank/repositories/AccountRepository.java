package hei.shool.bank.repositories;

import hei.shool.bank.entites.Account;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.CrudOperationsImplement;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class AccountRepository extends CrudOperationsImplement<Account, Long> {
    public AccountRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
