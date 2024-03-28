package hei.shool.bank.dtos.requests;

public record TransferRequest (
        String senderAccountNumber,
        String receiverAccountNumber,

        Double amount,

        String description
){}
