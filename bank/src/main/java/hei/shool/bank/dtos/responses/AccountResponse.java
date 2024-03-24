package hei.shool.bank.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountResponse(
        Long id,
        String password,

        String lastName,

        String firstName,

        LocalDate birthday,

        BigDecimal balance,

        BigDecimal netMonthlySalary,

        String accountNumber,

        boolean overdraftEnabled,

        LocalDate creationDate,

        LocalDate lastWithdrawalDate
){}
