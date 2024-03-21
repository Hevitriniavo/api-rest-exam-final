package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateAccountRequest(
         String password,
         String lastName,
         String firstName,
         String email,
         LocalDate birthday,
         BigDecimal balance,
         BigDecimal netMonthlySalary,
         Long bankId
){}
