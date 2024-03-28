package hei.shool.bank.dtos.responses;

import java.time.LocalDate;

public record AccountResponse(
        Long id,
        String password,

        String lastName,

        String firstName,
        String email,

        LocalDate birthday,

        Double balance,

        Double netMonthlySalary,

        String accountNumber,

        boolean overdraftEnabled,
        Double overdraftLimit,

        LocalDate creationDate,

        LocalDate lastWithdrawalDate
){}
