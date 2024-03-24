package hei.shool.bank.repositories;

import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.dtos.responses.TransactionResponse;
import hei.shool.bank.entities.Transaction;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.helpers.DatabaseHelper;
import hei.shool.bank.helpers.Paginate;
import hei.shool.bank.repositories.implementations.AbstractCrudOperations;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository extends AbstractCrudOperations<Transaction, Long> {

    private final Connection connection;
    private final DatabaseHelper databaseHelper;

    public TransactionRepository(DatabaseHelper databaseHelper, Connection connection) {
        super(databaseHelper, connection);
        this.connection = connection;
        this.databaseHelper = databaseHelper;
    }

    public List<TransactionResponse> findTransactionByAccountId(Long accountId) {
        String query = databaseHelper.getQueryByAccountId();
        return executeTransactionQuery(query, accountId);
    }

    public Paginate<TransactionResponse> paginateTransactionsByAccountId(Long accountId, Long pageNumber, Long pageSize) {
        String countQuery = databaseHelper.getQueryCountTransactions();
        Long totalCount = executeCountQuery(countQuery, accountId);
        String query = databaseHelper.getPaginateQuery();
        return executePaginationQuery(query, accountId, pageNumber, pageSize, totalCount);
    }

    private List<TransactionResponse> executeTransactionQuery(String query, Long accountId) {
        List<TransactionResponse> transactions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapToTransactionResponse(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    private Long executeCountQuery(String countQuery, Long accountId) {
        try (PreparedStatement stmt = connection.prepareStatement(countQuery)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("total_count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    private Paginate<TransactionResponse> executePaginationQuery(String query, Long accountId, Long pageNumber, Long pageSize, Long totalCount) {
        List<TransactionResponse> transactions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, accountId);
            stmt.setLong(2, pageSize);
            stmt.setLong(3, (pageNumber - 1) * pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapToTransactionResponse(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        long totalRowCount = getCountQuery(databaseHelper.getCountQuery(), accountId);
        long totalPages = (long) Math.ceil((double) totalCount / pageSize);
        Long next = (pageNumber < totalPages) ? pageNumber + 1 : null;
        Long previous = (pageNumber > 1) ? pageNumber - 1 : null;
        return new Paginate<>(totalRowCount, next, previous, transactions);
    }

    private TransactionResponse mapToTransactionResponse(ResultSet rs) throws SQLException {
        AccountResponse accountResponse = new AccountResponse(
                rs.getLong("id"),
                rs.getString("password"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("email"),
                rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null,
                rs.getBigDecimal("balance"),
                rs.getBigDecimal("net_monthly_salary"),
                rs.getString("account_number"),
                rs.getBoolean("overdraft_enabled"),
                rs.getBigDecimal("overdraft_limit"),
                rs.getDate("creation_date") != null ? rs.getDate("creation_date").toLocalDate() : null,
                rs.getDate("last_withdrawal_date") != null ? rs.getDate("last_withdrawal_date").toLocalDate() : null
        );
        return new TransactionResponse(
                rs.getLong("account_id"),
                accountResponse,
                rs.getBigDecimal("amount"),
                rs.getString("reason"),
                TransactionType.valueOf(rs.getString("transaction_type")),
                rs.getLong("category_id"),
                rs.getString("comment")
        );
    }

    private Long getCountQuery(String countQuery, Long accountId) {
        try (PreparedStatement stmt = connection.prepareStatement(countQuery)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

}
