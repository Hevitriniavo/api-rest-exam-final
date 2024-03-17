package hei.shool.bank.helpers;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHelperTest {

    private DatabaseHelper databaseHelper;

    @BeforeEach
    void setUp() {
        databaseHelper = new DatabaseHelper();
    }
    @Test
    void querySelectAll() {
        Class<AccountTest> entityClass = AccountTest.class;
        String query = databaseHelper.querySelectAll(entityClass);
        String expectedQuery = "SELECT id, customer_firstname, customer_lastname, birthday, net_monthly_salary, account_number FROM account_tests";
        assertEquals(expectedQuery, query);
    }

    @Test
    void querySelectById() {
        Class<AccountTest> entityClass = AccountTest.class;
        String query = databaseHelper.querySelectById(entityClass);
        String expectedQuery = "SELECT id, customer_firstname, customer_lastname, birthday, net_monthly_salary, account_number FROM account_tests WHERE id = ?";
        assertEquals(expectedQuery, query);
    }

    @Test
    void queryInsert() {
        Class<AccountTest> entityClass = AccountTest.class;
        String query = databaseHelper.queryInsert(entityClass);
        String expectedQuery = "INSERT INTO account_tests ( customer_firstname, customer_lastname, birthday, net_monthly_salary, account_number) VALUES ( ?, ?, ?, ?, ?)";
        assertEquals(expectedQuery, query);
    }

    @Test
    void queryUpdate() {
        Class<AccountTest> entityClass = AccountTest.class;
        String query = databaseHelper.queryUpdate(entityClass);
        String expectedQuery = "UPDATE account_tests SET customer_firstname = ?, customer_lastname = ?, birthday = ?, net_monthly_salary = ?, account_number = ? WHERE id = ?";
        assertEquals(expectedQuery, query);
    }

    @Test
    void queryDelete() {
        Class<AccountTest> entityClass = AccountTest.class;
        String query = databaseHelper.queryDeleteById(entityClass);
        String expectedQuery = "DELETE FROM account_tests WHERE id = ?";
        assertEquals(expectedQuery, query);
    }

    @Test
    void toSnackCase() {
        Class<AccountTest> entityClass = AccountTest.class;
        String query = databaseHelper.toSnackCase(entityClass.getSimpleName());
        String expectedQuery = "account_test";
        assertEquals(expectedQuery, query);
    }


    @Getter
    @Setter
    private static class AccountTest implements Identifiable<Long> {
        @Id
        @GeneratedValue
        private Long id;

        private String customerFirstname;

        private String customerLastname;

        private LocalDate birthday;

        private Double netMonthlySalary;

        private Long accountNumber;
        @Override
        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public Long getId() {
            return this.id;
        }
    }
}