package hei.shool.bank.config;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseSetting {

    private final DatabaseParam config;


    public DatabaseSetting(DatabaseParam config) {
        this.config = config;
    }


    protected void closeConnection() {
        try {
            config.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() {
        try {
            return config.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
