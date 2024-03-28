package hei.shool.bank.repositories;

import hei.shool.bank.entities.BankSoldHistory;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class BankSoldHistoryRepository extends AbstractCrudOperations<BankSoldHistory, Long> {
    public BankSoldHistoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
