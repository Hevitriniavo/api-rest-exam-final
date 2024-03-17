package hei.shool.bank.dtos.responses;

public record AccountResponse(
        Long id,
        Double balance,

        Double netMonthlySalary,

        String accountNumber,

        Double overdraftLimit,

        boolean overdraftEnabled
) { }
