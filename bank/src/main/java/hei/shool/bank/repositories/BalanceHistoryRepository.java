package hei.shool.bank.repositories;

import hei.shool.bank.helpers.DatabaseHelper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

@Repository
public class BalanceHistoryRepository {
    private final Connection connection;
    private final DatabaseHelper databaseHelper;

    public BalanceHistoryRepository(Connection connection, DatabaseHelper databaseHelper) {
        this.connection = connection;
        this.databaseHelper = databaseHelper;
    }

    public BigDecimal getPrincipalBalance(Long accountId, LocalDate selectedDate) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String query = """
                SELECT SUM(
                    CASE WHEN transaction_type = 'CREDIT' THEN amount ELSE -amount END
                    ) AS principal_balance
                FROM transactions WHERE account_id = ? AND effective_date <= ?
                """;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, accountId);
            stmt.setObject(2, selectedDate);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("principal_balance");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }
        return BigDecimal.ZERO;
    }

    public Map<String , BigDecimal> getLoansBalanceAndInterest(Long accountId, LocalDate selectedDate) {
        String query = """
                SELECT SUM(CASE WHEN amount IS NOT NULL THEN amount ELSE 0 END) AS loans_balance,
                       SUM(CASE WHEN interest_rate IS NOT NULL THEN interest_rate ELSE 0 END) AS loans_interest
                FROM interests
                WHERE account_id = ? AND interest_date <= ?
                """;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, accountId);
            stmt.setObject(2, selectedDate);
            rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal loansBalance = rs.getBigDecimal("loans_balance");
                BigDecimal loansInterest = rs.getBigDecimal("loans_interest");
                loansBalance = loansBalance != null ? loansBalance : BigDecimal.ZERO;
                loansInterest = loansInterest != null ? loansInterest : BigDecimal.ZERO;
                return Map.of("loansBalance", loansBalance, "loansInterest", loansInterest);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            databaseHelper.closeResources(stmt, rs);
        }
         return Map.of("loansBalance", BigDecimal.ZERO, "loansInterest", BigDecimal.ZERO);
    }
}
