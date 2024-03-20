package hei.shool.bank.repositories;

import hei.shool.bank.entites.Category;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class CategoryRepository extends AbstractCrudOperations<Category, Long> {
    public CategoryRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
    }
}
