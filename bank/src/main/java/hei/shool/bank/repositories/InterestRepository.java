package hei.shool.bank.repositories;

import hei.shool.bank.entities.Interest;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class InterestRepository extends AbstractCrudOperations<Interest, Long> {
    public InterestRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
