package hei.shool.bank.dtos.requests;

public record AccountRequest(
     Long id,
     Double balance,

     Double netMonthlySalary,

     String accountNumber,

     Double overdraftLimit,

     boolean overdraftEnabled

){}
