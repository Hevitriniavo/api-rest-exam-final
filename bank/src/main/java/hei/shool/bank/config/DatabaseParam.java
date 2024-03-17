package hei.shool.bank.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@Getter
public class DatabaseParam {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                this.url,
                this.username,
                this.password
        );
    }


}
