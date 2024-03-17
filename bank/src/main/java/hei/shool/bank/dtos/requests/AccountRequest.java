package hei.shool.bank.dtos.requests;

public record AccountRequest(
     Double balance,

     Double netMonthlySalary,

     String accountNumber,

     Double overdraftLimit,

     boolean overdraftEnabled

){}
