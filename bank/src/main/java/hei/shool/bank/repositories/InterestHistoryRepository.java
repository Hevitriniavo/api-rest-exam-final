package hei.shool.bank.repositories;

import hei.shool.bank.entities.InterestHistory;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class InterestHistoryRepository extends AbstractCrudOperations<InterestHistory, Long> {
    public InterestHistoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
