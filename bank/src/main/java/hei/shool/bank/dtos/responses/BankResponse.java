package hei.shool.bank.dtos.responses;


public record BankResponse(
        Long id,

        String name,

        String address,

        String phone,

        String email
) {}
