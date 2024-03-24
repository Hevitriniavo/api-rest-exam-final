package hei.shool.bank.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountResponse(
        Long id,
        String password,

        String lastName,

        String firstName,
        String email,

        LocalDate birthday,

        BigDecimal balance,

        BigDecimal netMonthlySalary,

        String accountNumber,

        boolean overdraftEnabled,
         BigDecimal overdraftLimit,

        LocalDate creationDate,

        LocalDate lastWithdrawalDate
){}
