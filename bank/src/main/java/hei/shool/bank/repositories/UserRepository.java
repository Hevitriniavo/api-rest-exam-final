package hei.shool.bank.repositories;

import hei.shool.bank.entites.User;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class UserRepository extends AbstractCrudOperations<User, Long> {
    public UserRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
