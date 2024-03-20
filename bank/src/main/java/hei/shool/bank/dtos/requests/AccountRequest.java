package hei.shool.bank.dtos.requests;

public record AccountRequest(
     Long id,
     Double balance,

     Double netMonthlySalary,

     Long userId
){}
