package hei.shool.bank.dtos.requests;

import java.time.LocalDate;

public record AccountRequest (
         Long id,
         String password,

         String lastName,

         String firstName,

         String email,

         LocalDate birthday,

         Double netMonthlySalary

){}
