package hei.shool.bank.repositories;

import hei.shool.bank.entities.Transfer;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class TransferRepository extends AbstractCrudOperations<Transfer, Long> {
    public TransferRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
